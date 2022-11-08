package com.example.vote.repository.entity.locationOption;

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
@Table(name="location_options")
public class LocationOption {

    @Id
    @Column("option_id")
    private Long optionId;

    @Column("vote_id")
    private Long voteId;

    @Column("play_location")
    private String playLocation;
}
