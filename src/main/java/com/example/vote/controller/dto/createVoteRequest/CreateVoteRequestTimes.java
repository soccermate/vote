package com.example.vote.controller.dto.createVoteRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateVoteRequestTimes
{
    @Min(0)
    @Max(23)
    private final int hour;

    @Min(0)
    @Max(59)
    private final int minute;

}
