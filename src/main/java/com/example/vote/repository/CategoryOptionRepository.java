package com.example.vote.repository;

import com.example.vote.repository.entity.categoryOption.CategoryOption;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CategoryOptionRepository extends ReactiveCrudRepository<CategoryOption, Long>
{
    public Mono<Integer> deleteByVoteId(Long voteId);
}
