package com.example.vote.repository.dto.voteDetail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationOptionDetail {
    @JsonProperty("option_id")
    private Long optionId;

    @JsonProperty("vote_id")
    private Long voteId;

    @JsonProperty("play_location")
    private String playLocation;

    @JsonProperty("vote_members")
    private List<Long> voteMembers;
}
