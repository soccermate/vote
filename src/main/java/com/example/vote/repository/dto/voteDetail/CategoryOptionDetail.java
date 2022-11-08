package com.example.vote.repository.dto.voteDetail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOptionDetail {
    @JsonProperty("option_id")
    private Long optionId;

    @JsonProperty("vote_id")
    private Long voteId;

    @JsonProperty("play_category")
    private String playCategory ;

    @JsonProperty("vote_members")
    private List<Long> voteMembers;
}
