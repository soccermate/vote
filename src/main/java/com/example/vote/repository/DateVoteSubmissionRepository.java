package com.example.vote.repository;

import com.example.vote.repository.entity.categoryVoteSubmission.CategoryVoteSubmission;
import com.example.vote.repository.entity.dateVoteSubmission.DateVoteSubmission;
import com.example.vote.repository.entity.dateVoteSubmission.DateVoteSubmissionPk;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface DateVoteSubmissionRepository extends ReactiveCrudRepository<DateVoteSubmission, DateVoteSubmissionPk>
{

    @Modifying
    @Transactional
    @Query("insert into date_vote_submission(vote_id, option_id, user_id) " +
            "values(:#{#dateVoteSubmission.voteId}, :#{#dateVoteSubmission.optionId}, :#{#dateVoteSubmission.userId})  " +
            "on conflict (vote_id, user_id)" +
            "do update set option_id = :#{#dateVoteSubmission.optionId};")
    public Mono<Integer> save(DateVoteSubmission dateVoteSubmission);

    public Mono<Integer> deleteByUserIdAndVoteId(Long userId, Long voteId);
}
