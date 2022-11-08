package com.example.vote.service;

import com.example.vote.exceptions.DuplicateOptionsException;
import com.example.vote.exceptions.OptionNotExistException;
import com.example.vote.repository.*;
import com.example.vote.repository.dto.voteDetail.VoteDetail;
import com.example.vote.repository.entity.Vote;
import com.example.vote.repository.entity.categoryOption.CategoryOption;
import com.example.vote.repository.entity.categoryVoteSubmission.CategoryVoteSubmission;
import com.example.vote.repository.entity.dateOption.DateOption;
import com.example.vote.repository.entity.dateVoteSubmission.DateVoteSubmission;
import com.example.vote.repository.entity.locationOption.LocationOption;
import com.example.vote.repository.entity.locationVoteSubmission.LocationVoteSubmission;
import com.example.vote.repository.entity.timesOption.TimesOption;
import com.example.vote.repository.entity.timesVoteSubmission.TimesVoteSubmission;
import com.example.vote.service.dto.CreateVoteDates;
import com.example.vote.service.dto.CreateVoteServiceDto;
import com.example.vote.service.dto.CreateVoteTimes;
import com.example.vote.service.dto.SubmitVoteServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final CategoryVoteSubmissionRepository categoryVoteSubmissionRepository;

    private final CategoryOptionRepository categoryOptionRepository;

    private final DateVoteSubmissionRepository dateVoteSubmissionRepository;

    private final DateOptionRepository dateOptionRepository;

    private final LocationVoteSubmissionRepository locationVoteSubmissionRepository;

    private final LocationOptionRepository locationOptionRepository;

    private final TimesVoteSubmissionRepository timesVoteSubmissionRepository;

    private final TimesOptionRepository timesOptionRepository;


    @Transactional("r2dbcTransactionManager")
    public Mono<Vote> createVote(CreateVoteServiceDto createVoteServiceDto)
    {
        Vote vote = Vote.builder()
                .creatorId(createVoteServiceDto.getCreatorId())
                .createdTime(LocalDateTime.now())
                .groupId(createVoteServiceDto.getGroupId())
                .title(createVoteServiceDto.getTitle())
                .build();
        return voteRepository.save(vote)
                .flatMap(savedVote ->{
                    Long voteId = savedVote.getVoteId();
                    List<Mono<TimesOption>> timeOptionsMonoList = new ArrayList<>();
                    List<Mono<CategoryOption>> categoryOptionsMonoList = new ArrayList<>();
                    List<Mono<DateOption>> dateOptionsMonoList = new ArrayList<>();
                    List<Mono<LocationOption>> locationOptionsMonoList = new ArrayList<>();

                    for(CreateVoteTimes createVoteTimes: createVoteServiceDto.getCreateVoteTimes())
                    {
                        TimesOption timesOption = TimesOption.builder()
                                .voteId(voteId)
                                .playTime(LocalTime.of(createVoteTimes.getHour(), createVoteTimes.getMinute()))
                                .build();
                        timeOptionsMonoList.add(timesOptionRepository.save(timesOption));
                    }

                    for(CreateVoteDates createVoteDates: createVoteServiceDto.getCreateVoteDates())
                    {
                        DateOption dateOption = DateOption.builder()
                                .voteId(voteId)
                                .playDate(LocalDate.of(createVoteDates.getYear(), createVoteDates.getMonth(), createVoteDates.getDay()))
                                .build();

                        dateOptionsMonoList.add(dateOptionRepository.save(dateOption));
                    }

                    for(String location: createVoteServiceDto.getLocations())
                    {
                        LocationOption locationOption = LocationOption.builder()
                                .voteId(voteId)
                                .playLocation(location)
                                .build();

                        locationOptionsMonoList.add(locationOptionRepository.save(locationOption));
                    }

                    for(String category: createVoteServiceDto.getCategories())
                    {
                        CategoryOption categoryOption = CategoryOption.builder()
                                .voteId(voteId)
                                .playCategory(category)
                                .build();

                        categoryOptionsMonoList.add(categoryOptionRepository.save(categoryOption));
                    }

                    return Flux.zip(
                            Flux.concat(timeOptionsMonoList),
                            Flux.concat(categoryOptionsMonoList),
                            Flux.concat(locationOptionsMonoList),
                            Flux.concat(dateOptionsMonoList))
                            .collectList()
                            .map(result ->{
                                return savedVote;
                            });
                })
                .onErrorResume(throwable -> {
                    if(throwable instanceof DataIntegrityViolationException)
                    {
                        return Mono.error(new DuplicateOptionsException("you cannot create vote with duplicate options!"));
                    }
                    else{
                        return Mono.error(throwable);
                    }
                });
    }

    public Flux<Vote> getVotesByGroupId(Long groupId, Pageable pageable)
    {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("voteId").descending() );

        return voteRepository.findByGroupId(groupId, sortedPageable);
    }

    public Mono<VoteDetail> getVoteDetailByVoteId(Long voteId)
    {
        return voteRepository.getVoteDetail(voteId);
    }

    public Mono<Boolean> submitVote(SubmitVoteServiceDto submitVoteServiceDto)
    {
        Long userId = submitVoteServiceDto.getUserId();
        Long voteId = submitVoteServiceDto.getVoteId();

        TimesVoteSubmission timesVoteSubmission = TimesVoteSubmission.builder()
                .userId(userId)
                .voteId(voteId)
                .optionId(submitVoteServiceDto.getTimesOptionId())
                .build();

        DateVoteSubmission dateVoteSubmission = DateVoteSubmission.builder()
                .userId(userId)
                .voteId(voteId)
                .optionId(submitVoteServiceDto.getDateOptionId())
                .build();

        LocationVoteSubmission locationVoteSubmission = LocationVoteSubmission.builder()
                .userId(userId)
                .voteId(voteId)
                .optionId(submitVoteServiceDto.getLocationOptionId())
                .build();

        CategoryVoteSubmission categoryVoteSubmission= CategoryVoteSubmission.builder()
                .userId(userId)
                .voteId(voteId)
                .optionId(submitVoteServiceDto.getCategoryOptionId())
                .build();

        Mono<Integer> saveTimesVoteSubmission = timesVoteSubmissionRepository.save(timesVoteSubmission);
        Mono<Integer> saveDateVoteSubmission = dateVoteSubmissionRepository.save(dateVoteSubmission);
        Mono<Integer> saveLocationVoteSubmission = locationVoteSubmissionRepository.save(locationVoteSubmission);
        Mono<Integer> saveCategoryVoteSubmission = categoryVoteSubmissionRepository.save(categoryVoteSubmission);

        return Flux.zip(saveTimesVoteSubmission, saveCategoryVoteSubmission, saveDateVoteSubmission, saveLocationVoteSubmission)
                .collectList().map(result -> Boolean.valueOf(true))
                .onErrorResume(throwable -> {
                    if(throwable instanceof DataIntegrityViolationException)
                    {
                        return Mono.error(new OptionNotExistException("vote might not exist or optionId that you provided might not exist or the optionIds you provided might not be associated with voteId " + voteId));
                    }
                    else{
                        return Mono.error(throwable);
                    }
                });
    }

    public Mono<Boolean> deleteVoteSubmission( Long userId, Long voteId)
    {
        Mono<Integer> deleteTimesVoteSubmission = timesVoteSubmissionRepository.deleteByUserIdAndVoteId(userId, voteId);
        Mono<Integer> deleteDateVoteSubmission = dateVoteSubmissionRepository.deleteByUserIdAndVoteId(userId, voteId);
        Mono<Integer> deleteLocationVoteSubmission = locationVoteSubmissionRepository.deleteByUserIdAndVoteId(userId, voteId);
        Mono<Integer> deleteCategoryVoteSubmission = categoryVoteSubmissionRepository.deleteByUserIdAndVoteId(userId, voteId);

        return Flux.zip(deleteCategoryVoteSubmission, deleteLocationVoteSubmission, deleteDateVoteSubmission, deleteTimesVoteSubmission)
                .collectList()
                .map(result -> Boolean.valueOf(true));
    }

    public Mono<Boolean> checkIfVoteExists(Long voteId, Long groupId)
    {
        return voteRepository.existsByVoteIdAndGroupId(voteId, groupId);
    }


}
