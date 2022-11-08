package com.example.vote.repository.dto.voteDetail;

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
public class VoteDetail {

    private Long groupId;

    private Long voteId;

    private LocalDateTime createdTime;

    private String title;

    private List<CategoryOptionDetail> categoryOptionDetail;

    private List<LocationOptionDetail> locationOptionDetail;

    private List<DateOptionDetail> dateOptionDetail;

    private List<TimesOptionDetail> timesOptionDetail;

}
