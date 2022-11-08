package com.example.vote.controller.dto.getVoteDetailResponse;

import com.example.vote.repository.dto.voteDetail.TimesOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class GetVoteDetailTimesResponse
{
    private final Long option_id;

    private final int hour;

    private final int minute;

    private List<Long> vote_members;


    public GetVoteDetailTimesResponse(TimesOptionDetail timesOptionDetail)
    {
        LocalTime localTime = LocalTime.parse(timesOptionDetail.getPlayTime());

        this.option_id = timesOptionDetail.getOptionId();
        this.hour = localTime.getHour();
        this.minute = localTime.getMinute();
        this.vote_members = timesOptionDetail.getVoteMembers();
    }

}
