package com.example.vote.controller.dto.getVoteResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class GetVoteResponse
{
    private final String title;

    private final Long vote_id;

    private final LocalDateTime created_time;

}
