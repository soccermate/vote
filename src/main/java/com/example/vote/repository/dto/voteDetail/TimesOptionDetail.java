package com.example.vote.repository.dto.voteDetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesOptionDetail
{
    @JsonProperty("option_id")
    private Long optionId;

    @JsonProperty("vote_id")
    private Long voteId;

    @JsonFormat(pattern="hh:mm:ss")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonProperty("play_time")
    private String playTime;

    @JsonProperty("vote_members")
    private List<Long> voteMembers;


}
