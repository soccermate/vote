package com.example.vote.repository.entity.dateVoteSubmission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="date_vote_submission")
public class DateVoteSubmission {

    @Column("vote_id")
    private Long voteId;

    @Column("option_id")
    private Long optionId;

    @Column("user_id")
    private Long userId;
}
