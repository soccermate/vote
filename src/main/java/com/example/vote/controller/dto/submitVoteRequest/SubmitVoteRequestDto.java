package com.example.vote.controller.dto.submitVoteRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SubmitVoteRequestDto {

    @Min(value = 0, message = "date_option_id cannot be smaller than 0")
    private Long date_option_id;

    @Min(value = 0, message = "times_option_id cannot be smaller than 0")
    private Long times_option_id;

    @Min(value = 0, message = "location_option_id cannot be smaller than 0")
    private Long location_option_id;

    @Min(value = 0, message = "category_option_id cannot be smaller than 0")
    private Long category_option_id;

}
