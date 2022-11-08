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
public class CreateVoteRequestDates
{

    @Min(value = 2022, message =  "year should be equal to 2022 or greater")
    private final Integer year;

    @Min(value = 1, message = "month should be equal or greater than 1")
    @Max(value = 12, message = "month should be equal or less than 12")
    private final Integer month;

    @Min(value = 1, message = "day should be equal or greater than 1")
    @Max(value = 31, message =  "day should be equal or less than 31")
    private final Integer day;
}
