package com.example.vote.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateVoteTimes {
    private final int hour;
    private final int minute;
}
