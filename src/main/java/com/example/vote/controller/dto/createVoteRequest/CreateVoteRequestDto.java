package com.example.vote.controller.dto.createVoteRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateVoteRequestDto
{
    @Size(min= 2, message =  "category options must be at least 2")
    private final List<String> categories;


    @Size(min= 1, message =  "location options must be at least 1")
    private final List<String> locations;

    @NotBlank(message = "title cannot be blank!")
    @Size(min = 3, message =  "title should be at least 3 characters long!")
    private final String title;

    @Valid
    @Size(min= 1, message =  "date options must be at least 1")
    private final List<CreateVoteRequestDates> dates;

    @Valid
    @Size(min= 1, message =  "time options must be at least 1")
    private final List<CreateVoteRequestTimes> times;






}
