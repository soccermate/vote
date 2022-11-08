package com.example.vote;

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
import com.example.vote.service.VoteService;
import com.example.vote.service.dto.CreateVoteDates;
import com.example.vote.service.dto.CreateVoteServiceDto;
import com.example.vote.service.dto.CreateVoteTimes;
import com.example.vote.service.dto.SubmitVoteServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Slf4j
@EnableConfigurationProperties
@ActiveProfiles(profiles = "dev")
class VoteApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	TimesOptionRepository timesOptionRepository;

	@Autowired
	DateOptionRepository dateOptionRepository;

	@Autowired
	CategoryOptionRepository categoryOptionRepository;

	@Autowired
	LocationOptionRepository locationOptionRepository;

	@Test
	void testDateOptionsRepository()
	{
		DateOption dateOption = DateOption.builder()
				.voteId(Long.valueOf(1))
				.playDate(LocalDate.of(2022, 03, 30))
				.build();

		Mono<DateOption> dateOptionMono = dateOptionRepository.save(dateOption).log();

		StepVerifier.create(dateOptionMono).expectNextCount(1).verifyComplete();
	}

	@Test
	void testLocationOptionsRepository()
	{
		LocationOption locationOption = LocationOption.builder()
				.voteId(Long.valueOf(1))
				.playLocation("서울시 성동구")
				.build();

		Mono<LocationOption> dateOptionMono = locationOptionRepository.save(locationOption).log();

		StepVerifier.create(dateOptionMono).expectNextCount(1).verifyComplete();
	}

	@Test
	void testCategoryOptionsRepository()
	{
		CategoryOption categoryOption = CategoryOption.builder()
				.voteId(Long.valueOf(1))
				.playCategory("축구")
				.build();

		Mono<CategoryOption> dateOptionMono = categoryOptionRepository.save(categoryOption).log();

		StepVerifier.create(dateOptionMono).expectNextCount(1).verifyComplete();
	}


	@Test
	void testTimesOptionsRepository()
	{
		/*

		Flux<TimesOption> timesOptionsFlux = timesOptionRepository.findByVoteId(Long.valueOf(1)).log("INFO");

		StepVerifier.create(timesOptionsFlux).expectNextCount(1).verifyComplete();


		 */


		/*
		Mono<Integer> deletedCounts = timesOptionRepository.deleteByVoteId(Long.valueOf(1));

		StepVerifier.create(deletedCounts).expectNextCount(1).verifyComplete();


		 */





		TimesOption timesOption = TimesOption.builder()
				.voteId(Long.valueOf(1))
				.playTime(LocalTime.of(17, 30))
				.build();

		Mono<TimesOption> savedTimesOption = timesOptionRepository.save(timesOption).log();

		StepVerifier.create(savedTimesOption).expectNextCount(1).verifyComplete();



	}

	@Autowired
	TimesVoteSubmissionRepository timesVoteSubmissionRepository;

	@Autowired
	LocationVoteSubmissionRepository locationVoteSubmissionRepository;

	@Autowired
	DateVoteSubmissionRepository dateVoteSubmissionRepository;

	@Autowired
	CategoryVoteSubmissionRepository categoryVoteSubmissionRepository;


	@Test
	void testTimesVoteSubmissionRepository()
	{
		TimesVoteSubmission timesVoteSubmission = TimesVoteSubmission.builder()
				.voteId(Long.valueOf(1))
				.optionId(Long.valueOf(5))
				.userId(Long.valueOf(2))
				.build();

		Mono<Integer> sub = timesVoteSubmissionRepository.save(timesVoteSubmission).log();

		StepVerifier.create(sub).expectNextCount(1).verifyComplete();
	}

	@Test
	void testDateVoteSubmissionRepository()
	{
		DateVoteSubmission dateVoteSubmission = DateVoteSubmission.builder()
				.voteId(Long.valueOf(1))
				.optionId(Long.valueOf(1))
				.userId(Long.valueOf(2))
				.build();

		Mono<Integer> sub = dateVoteSubmissionRepository.save(dateVoteSubmission).log();

		StepVerifier.create(sub).expectNextCount(1).verifyComplete();
	}

	@Test
	void testLocationVoteSubmissionRepository()
	{
		LocationVoteSubmission locationVoteSubmission = LocationVoteSubmission.builder()
				.voteId(Long.valueOf(1))
				.optionId(Long.valueOf(1))
				.userId(Long.valueOf(2))
				.build();

		Mono<Integer> sub = locationVoteSubmissionRepository.save(locationVoteSubmission).log();

		StepVerifier.create(sub).expectNextCount(1).verifyComplete();
	}

	@Test
	void testCategoryVoteSubmissionRepository()
	{
		CategoryVoteSubmission categoryVoteSubmission = CategoryVoteSubmission.builder()
				.voteId(Long.valueOf(1))
				.optionId(Long.valueOf(1))
				.userId(Long.valueOf(2))
				.build();

		Mono<Integer> sub = categoryVoteSubmissionRepository.save(categoryVoteSubmission).log();

		StepVerifier.create(sub).expectNextCount(1).verifyComplete();
	}

	@Autowired
	VoteRepository voteRepository;

	@Test
	void testVoteDetail()
	{
		Mono<VoteDetail> voteDetailMono = voteService.getVoteDetailByVoteId(Long.valueOf(6)).log();

		StepVerifier.create(voteDetailMono).expectNextCount(1).verifyComplete();
	}

	@Autowired
	VoteService voteService;

	@Test
	void testVoteService()
	{
		CreateVoteServiceDto createVoteServiceDto = CreateVoteServiceDto.builder()
				.createVoteTimes(List.of(
						CreateVoteTimes.builder().hour(1).minute(30).build(),
						CreateVoteTimes.builder().hour(3).minute(30).build()
				))
				.createVoteDates(List.of(
						CreateVoteDates.builder().year(2022).month(7).day(20).build(),
						CreateVoteDates.builder().year(2022).month(8).day(30).build(),
						CreateVoteDates.builder().year(2022).month(10).day(10).build()
				))
				.creatorId(Long.valueOf(4))
				.title("second vote for groupId 10")
				.groupId(Long.valueOf(10))
				.categories(List.of("축구", "풋살"))
				.locations(List.of("성동구 곤", "관악구", "도봉구"))
				.build();

		StepVerifier.create(voteService.createVote(createVoteServiceDto))
				.expectNextCount(1).verifyComplete();
	}

	@Test
	void testGetVotesByGroupId()
	{
		Pageable pageable = PageRequest.of(0, 10);

		Flux<Vote> voteFlux = voteService.getVotesByGroupId(Long.valueOf(10), pageable).log();

		StepVerifier.create(voteFlux).expectNextCount(1).verifyComplete();
	}

	@Test
	void testSubmitVote()
	{
		SubmitVoteServiceDto submitVoteServiceDto = SubmitVoteServiceDto.builder()
				.voteId(Long.valueOf(1))
				.userId(Long.valueOf(3))
				.categoryOptionId(Long.valueOf(7))
				.locationOptionId(Long.valueOf(7))
				.dateOptionId(Long.valueOf(7))
				.timesOptionId(Long.valueOf(14))
				.build();

		StepVerifier.create(voteService.submitVote(submitVoteServiceDto)).expectNextCount(1).verifyComplete();
	}

	@Test
	void testDeleteVoteSubmission()
	{
		Mono<Boolean> deleteVoteSubmission = voteService.deleteVoteSubmission(Long.valueOf(3), Long.valueOf(6));

		StepVerifier.create(deleteVoteSubmission).expectNextCount(1).verifyComplete();
	}




}
