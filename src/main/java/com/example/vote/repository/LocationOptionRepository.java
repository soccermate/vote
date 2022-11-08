package com.example.vote.repository;

import com.example.vote.repository.entity.locationOption.LocationOption;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LocationOptionRepository extends ReactiveCrudRepository<LocationOption, Long>
{
    public Mono<Integer> deleteByVoteId(Long voteId);
}
