package com.example.vote.repository;

import com.example.vote.repository.entity.dateVoteSubmission.DateVoteSubmission;
import com.example.vote.repository.entity.locationVoteSubmission.LocationVoteSubmission;
import com.example.vote.repository.entity.locationVoteSubmission.LocationVoteSubmissionPk;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface LocationVoteSubmissionRepository extends ReactiveCrudRepository<LocationVoteSubmission, LocationVoteSubmissionPk> {

    @Modifying
    @Transactional
    @Query("insert into location_vote_submission(vote_id, option_id, user_id) " +
            "values(:#{#locationVoteSubmission.voteId}, :#{#locationVoteSubmission.optionId}, :#{#locationVoteSubmission.userId})  " +
            "on conflict (vote_id, user_id)" +
            "do update set option_id = :#{#locationVoteSubmission.optionId};")
    public Mono<Integer> save(LocationVoteSubmission locationVoteSubmission);

    public Mono<Integer> deleteByUserIdAndVoteId(Long userId, Long voteId);
}
