package com.example.vote.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="vote")
public class Vote {


    @Id
    @Column("vote_id")
    private Long voteId;

    @Column("group_id")
    private Long groupId;

    @Column("creator_id")
    private Long creatorId;

    @Column("title")
    private String title;

    @Column("created_time")
    private LocalDateTime createdTime;


}
