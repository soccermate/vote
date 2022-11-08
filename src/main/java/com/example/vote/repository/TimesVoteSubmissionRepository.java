package com.example.vote.repository;

import com.example.vote.repository.entity.timesVoteSubmission.TimesVoteSubmission;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface TimesVoteSubmissionRepository extends ReactiveCrudRepository<TimesVoteSubmission, TimesVoteSubmissionRepository>
{
    @Modifying
    @Transactional
    @Query("insert into times_vote_submission(vote_id, option_id, user_id) " +
            "values(:#{#timesVoteSubmission.voteId}, :#{#timesVoteSubmission.optionId}, :#{#timesVoteSubmission.userId})  " +
            "on conflict (vote_id, user_id)" +
            "do update set option_id = :#{#timesVoteSubmission.optionId};")
    public Mono<Integer> save(TimesVoteSubmission timesVoteSubmission);

    public Mono<Integer> deleteByUserIdAndVoteId(Long userId, Long voteId);
}
