package com.example.vote.controller.dto.getVoteDetailResponse;

import com.example.vote.repository.dto.voteDetail.CategoryOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GetVoteDetailCategoriesResponse
{
    private final Long option_id;

    private final String category_name;

    private final List<Long> vote_members;

    public GetVoteDetailCategoriesResponse(CategoryOptionDetail categoryOptionDetail)
    {
        this.option_id = categoryOptionDetail.getOptionId();
        this.category_name = categoryOptionDetail.getPlayCategory();
        this.vote_members = categoryOptionDetail.getVoteMembers();
    }
}
