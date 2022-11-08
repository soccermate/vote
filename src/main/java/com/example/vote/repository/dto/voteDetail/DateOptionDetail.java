package com.example.vote.repository.dto.voteDetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateOptionDetail {
    @JsonProperty("option_id")
    private Long optionId;

    @JsonProperty("vote_id")
    private Long voteId;


    //@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonProperty("play_date")
    private String playDate ;

    @JsonProperty("vote_members")
    private List<Long> voteMembers;

}
