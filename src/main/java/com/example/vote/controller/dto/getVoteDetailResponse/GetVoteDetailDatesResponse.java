package com.example.vote.controller.dto.getVoteDetailResponse;

import com.example.vote.repository.dto.voteDetail.DateOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public
class GetVoteDetailDatesResponse
{
    private final Long option_id;

    private final int year;

    private final int day;

    private final int month;

    private final List<Long> vote_members;

    public GetVoteDetailDatesResponse(DateOptionDetail dateOptionDetail)
    {
        LocalDate localDate = LocalDate.parse(dateOptionDetail.getPlayDate());
        this.option_id = dateOptionDetail.getOptionId();
        this.year = localDate.getYear();
        this.day = localDate.getDayOfMonth();
        this.month = localDate.getMonthValue();
        this.vote_members = dateOptionDetail.getVoteMembers();
    }

}
