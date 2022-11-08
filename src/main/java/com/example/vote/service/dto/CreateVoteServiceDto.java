package com.example.vote.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CreateVoteServiceDto {

    private final Long groupId;

    private final Long creatorId;

    private final String title;

    private final List<CreateVoteTimes> createVoteTimes;

    private final List<CreateVoteDates> createVoteDates;

    private final List<String> locations;

    private final List<String> categories;


}
