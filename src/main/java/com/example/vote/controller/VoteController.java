package com.example.vote.controller;

import com.example.vote.controller.dto.createVoteRequest.CreateVoteRequestDates;
import com.example.vote.controller.dto.createVoteRequest.CreateVoteRequestDto;
import com.example.vote.controller.dto.createVoteRequest.CreateVoteRequestTimes;
import com.example.vote.controller.dto.getVoteDetailResponse.*;
import com.example.vote.controller.dto.getVoteResponse.GetVoteResponse;
import com.example.vote.controller.dto.getVoteResponse.GetVotesResponseDto;
import com.example.vote.controller.dto.submitVoteRequest.SubmitVoteRequestDto;
import com.example.vote.controller.util.ObjectConverter;
import com.example.vote.controller.util.VerifyTokenResult;
import com.example.vote.exceptions.ResourceNotFoundException;
import com.example.vote.exceptions.UnAuthorizedActionException;
import com.example.vote.exceptions.VoteIdAndGroupIdNotMatchingException;
import com.example.vote.service.VoteService;
import com.example.vote.service.dto.CreateVoteDates;
import com.example.vote.service.dto.CreateVoteServiceDto;
import com.example.vote.service.dto.CreateVoteTimes;
import com.example.vote.service.dto.SubmitVoteServiceDto;
import com.example.vote.util.SoccerGroupFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.vote.config.GlobalStaticVariables.AUTH_CREDENTIALS;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("soccer-group")
public class VoteController {

    private final SoccerGroupFeignClient soccerGroupFeignClient;

    private final VoteService voteService;

    @PostMapping("{groupId}/votes")
    Mono<Void> createVote(
            @RequestHeader(AUTH_CREDENTIALS) String authStr,
            @Valid @RequestBody CreateVoteRequestDto createVoteRequestDto,
            @PathVariable Long groupId)
    {
        VerifyTokenResult verifyTokenResult = ObjectConverter.convertAuthCredentials(authStr);

        List<CreateVoteDates> createVoteDateList = new ArrayList<>();
        List<CreateVoteTimes> createVoteTimesList = new ArrayList<>();

        for(CreateVoteRequestDates createVoteRequestDate: createVoteRequestDto.getDates())
        {
            CreateVoteDates createVoteDates = CreateVoteDates.builder()
                    .day(createVoteRequestDate.getDay())
                    .month(createVoteRequestDate.getMonth())
                    .year(createVoteRequestDate.getYear())
                    .build();

            createVoteDateList.add(createVoteDates);
        }

        for(CreateVoteRequestTimes createVoteRequestTimes: createVoteRequestDto.getTimes())
        {
            CreateVoteTimes createVoteTimes = CreateVoteTimes.builder()
                    .hour(createVoteRequestTimes.getHour())
                    .minute(createVoteRequestTimes.getMinute())
                    .build();

            createVoteTimesList.add(createVoteTimes);
        }

        return soccerGroupFeignClient.getSoccerGroupDetail(authStr, groupId)
                .flatMap(soccerGroupDetailResponseDto -> {

                    if(!soccerGroupDetailResponseDto.getOwner_id().equals(verifyTokenResult.getUser_id()))
                    {
                        throw new UnAuthorizedActionException("user is not allowed to create vote in group " + groupId + " because he is not an owner");
                    }

                    CreateVoteServiceDto createVoteServiceDto = CreateVoteServiceDto.builder()
                            .locations(createVoteRequestDto.getLocations())
                            .createVoteDates(createVoteDateList)
                            .createVoteTimes(createVoteTimesList)
                            .creatorId(verifyTokenResult.getUser_id())
                            .categories(createVoteRequestDto.getCategories())
                            .title(createVoteRequestDto.getTitle())
                            .groupId(groupId)
                            .build();

                    return voteService.createVote(createVoteServiceDto);
                })
                .onErrorResume(throwable -> {

                    if(throwable instanceof FeignException.FeignClientException.NotFound)
                    {
                        return Mono.error( new ResourceNotFoundException("soccer group with id " + groupId + "not exists!"));
                    }

                    return Mono.error(throwable);
                })
                .flatMap(vote -> Mono.empty());
    }


    @GetMapping("{groupId}/votes")
    Mono<ResponseEntity<GetVotesResponseDto>> getVotes(
            @RequestHeader(AUTH_CREDENTIALS) String authStr,
            @PathVariable Long groupId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ){

        Pageable pageable = PageRequest.of(page, size);

        return voteService.getVotesByGroupId(groupId, pageable)
                .map(
                        vote -> {
                            return GetVoteResponse.builder()
                                    .vote_id(vote.getVoteId())
                                    .created_time(vote.getCreatedTime())
                                    .title(vote.getTitle())
                                    .build();
                        }
                )
                .collectList()
                .map(getVoteResponses -> {
                    return ResponseEntity.ok(GetVotesResponseDto
                            .builder()
                            .votes(getVoteResponses)
                            .build());
                });
    }



    @GetMapping("{groupId}/votes/{voteId}")
    Mono<ResponseEntity<GetVoteDetailResponseDto>> getVoteDetails(
            @RequestHeader(AUTH_CREDENTIALS) String authStr,
            @PathVariable Long groupId,
            @PathVariable Long voteId
    )
    {
        return voteService.getVoteDetailByVoteId(voteId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("vote with voteId " + voteId + " not exist!")))
                .map(voteDetail -> {
                    log.debug("starting! get voteDetail");
                    log.debug(voteDetail.toString());

                    if(!voteDetail.getGroupId().equals(groupId))
                    {
                        throw new VoteIdAndGroupIdNotMatchingException("group with groupId " + groupId + " does not have voteId " + voteId);
                    }

                    List<GetVoteDetailTimesResponse> getVoteDetailTimesResponses = voteDetail.getTimesOptionDetail()
                            .stream().map(GetVoteDetailTimesResponse::new).toList();

                    List<GetVoteDetailLocationsResponse> getVoteDetailLocationsResponses = voteDetail.getLocationOptionDetail()
                            .stream().map(GetVoteDetailLocationsResponse::new).toList();

                    List<GetVoteDetailDatesResponse> getVoteDetailDatesResponses = voteDetail.getDateOptionDetail()
                            .stream().map(GetVoteDetailDatesResponse::new).toList();

                    List<GetVoteDetailCategoriesResponse> getVoteDetailCategoriesResponses = voteDetail.getCategoryOptionDetail()
                            .stream().map(GetVoteDetailCategoriesResponse::new).toList();

                    String title = voteDetail.getTitle();
                    LocalDateTime createTime = voteDetail.getCreatedTime();

                    return ResponseEntity.ok(
                            GetVoteDetailResponseDto.builder()
                            .title(title)
                            .created_time(createTime)
                            .group_id(voteDetail.getGroupId())
                            .vote_id(voteDetail.getVoteId())
                            .times(getVoteDetailTimesResponses)
                            .dates(getVoteDetailDatesResponses)
                            .categories(getVoteDetailCategoriesResponses)
                            .locations(getVoteDetailLocationsResponses)
                            .build());
                });
    }

    @PostMapping("{groupId}/votes/{voteId}/vote-submission")
    Mono<Void> submitVote(
            @RequestHeader(AUTH_CREDENTIALS) String authStr,
            @PathVariable Long groupId,
            @PathVariable Long voteId,
            @Valid @RequestBody SubmitVoteRequestDto submitVoteRequestDto
            )
    {
        VerifyTokenResult verifyTokenResult = ObjectConverter.convertAuthCredentials(authStr);

        return voteService.checkIfVoteExists(voteId, groupId)
                .flatMap(doesExists -> {

                    if(!doesExists)
                    {
                        throw new VoteIdAndGroupIdNotMatchingException("group " +groupId + " does not have vote with " + voteId );
                    }

                    return soccerGroupFeignClient.getSoccerGroupDetail(authStr, groupId);
                })
                .flatMap(
                soccerGroupDetailResponseDto -> {

                    log.info(soccerGroupDetailResponseDto.toString());

                    if (!soccerGroupDetailResponseDto.getMembers().contains(verifyTokenResult.getUser_id()))
                    {
                        throw new UnAuthorizedActionException("user " + verifyTokenResult.getUser_id() + " is not in group " + groupId);
                    }



                    SubmitVoteServiceDto submitVoteServiceDto = SubmitVoteServiceDto.builder()
                            .timesOptionId(submitVoteRequestDto.getTimes_option_id())
                            .dateOptionId(submitVoteRequestDto.getDate_option_id())
                            .locationOptionId(submitVoteRequestDto.getLocation_option_id())
                            .categoryOptionId(submitVoteRequestDto.getCategory_option_id())
                            .voteId(voteId)
                            .userId(verifyTokenResult.getUser_id())
                            .build();

                    return voteService.submitVote(submitVoteServiceDto);
                })
                .onErrorResume(throwable -> {
                    if(throwable instanceof FeignException.FeignClientException.NotFound)
                    {
                        return Mono.error( new ResourceNotFoundException("soccer group with id " + groupId + "not exists!"));
                    }

                    return Mono.error(throwable);
                })
                .flatMap(result -> Mono.empty());
    }






}
