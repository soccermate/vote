package com.example.vote.controller.dto.getVoteResponse;
import com.example.vote.controller.dto.getVoteResponse.GetVoteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class GetVotesResponseDto
{
    List<GetVoteResponse> votes;
}
