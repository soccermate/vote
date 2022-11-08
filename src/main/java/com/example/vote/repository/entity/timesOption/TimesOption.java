package com.example.vote.repository.entity.timesOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="times_options")
public class TimesOption {

    @Column("vote_id")
    private Long voteId;

    @Id
    @Column("option_id")
    private Long optionId;

    @Column("play_time")
    private LocalTime playTime;

}
