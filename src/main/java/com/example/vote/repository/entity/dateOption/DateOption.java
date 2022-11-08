package com.example.vote.repository.entity.dateOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="date_options")
public class DateOption {

    @Id
    @Column("option_id")
    private Long optionId;

    @Column("vote_id")
    private Long voteId;

    @Column("play_date")
    private LocalDate playDate;
}
