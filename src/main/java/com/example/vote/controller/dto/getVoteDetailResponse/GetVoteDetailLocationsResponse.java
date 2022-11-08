package com.example.vote.controller.dto.getVoteDetailResponse;

import com.example.vote.repository.dto.voteDetail.LocationOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class GetVoteDetailLocationsResponse
{
    private final Long option_id;

    private final String location_name;

    private final List<Long> vote_members;

    public GetVoteDetailLocationsResponse(LocationOptionDetail locationOptionDetail)
    {
        this.option_id = locationOptionDetail.getOptionId();
        this.location_name = locationOptionDetail.getPlayLocation();
        this.vote_members = locationOptionDetail.getVoteMembers();
    }
}
