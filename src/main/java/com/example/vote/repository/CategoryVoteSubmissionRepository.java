package com.example.vote.repository;

import com.example.vote.repository.entity.categoryVoteSubmission.CategoryVoteSubmission;
import com.example.vote.repository.entity.categoryVoteSubmission.CategoryVoteSubmissionPk;
import com.example.vote.repository.entity.timesVoteSubmission.TimesVoteSubmission;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface CategoryVoteSubmissionRepository extends ReactiveCrudRepository<CategoryVoteSubmission, CategoryVoteSubmissionPk>
{
    @Modifying
    @Transactional
    @Query("insert into category_vote_submission(vote_id, option_id, user_id) " +
            "values(:#{#categoryVoteSubmission.voteId}, :#{#categoryVoteSubmission.optionId}, :#{#categoryVoteSubmission.userId})  " +
            "on conflict (vote_id, user_id)" +
            "do update set option_id = :#{#categoryVoteSubmission.optionId};")
    public Mono<Integer> save(CategoryVoteSubmission categoryVoteSubmission);

    public Mono<Integer> deleteByUserIdAndVoteId(Long userId, Long voteId);
}
