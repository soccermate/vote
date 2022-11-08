package com.example.vote.repository;

import com.example.vote.repository.entity.timesOption.TimesOption;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TimesOptionRepository extends ReactiveCrudRepository<TimesOption, Long>
{
    //@Query("SELECT * FROM TIMES_OPTIONS WHERE vote_id = :voteId")
    public Flux<TimesOption> findByVoteId(@Param("voteId") Long voteId);


    public Mono<Integer> deleteByVoteId(Long voteId);




}
