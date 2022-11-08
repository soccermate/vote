package com.example.vote.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateVoteDates
{
    private final int year;

    private final int month;

    private final int day;

}
