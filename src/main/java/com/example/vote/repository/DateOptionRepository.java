package com.example.vote.repository;

import com.example.vote.repository.entity.dateOption.DateOption;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DateOptionRepository extends ReactiveCrudRepository<DateOption, Long>
{

    public Mono<Integer> deleteByVoteId(Long voteId);
}
