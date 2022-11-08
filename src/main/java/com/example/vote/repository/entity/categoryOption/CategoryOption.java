package com.example.vote.repository.entity.categoryOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="category_options")
public class CategoryOption {

    @Id
    @Column("option_id")
    private Long optionId;

    @Column("vote_id")
    private Long voteId;

    @Column("play_category")
    private String playCategory;

}
