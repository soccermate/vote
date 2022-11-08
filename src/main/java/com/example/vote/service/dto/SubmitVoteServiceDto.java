package com.example.vote.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubmitVoteServiceDto
{
    private final Long voteId;

    private final Long userId;

    private final Long timesOptionId;

    private final Long dateOptionId;

    private final Long locationOptionId;

    private final Long categoryOptionId;


}
