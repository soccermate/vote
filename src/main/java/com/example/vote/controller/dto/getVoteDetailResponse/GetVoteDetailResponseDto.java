package com.example.vote.controller.dto.getVoteDetailResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class GetVoteDetailResponseDto
{

    private final Long vote_id;

    private final String title;

    private final LocalDateTime created_time;

    private final Long group_id;

    private final List<GetVoteDetailDatesResponse> dates;

    private final List<GetVoteDetailCategoriesResponse> categories;

    private final List<GetVoteDetailLocationsResponse> locations;

    private final List<GetVoteDetailTimesResponse> times;

}
