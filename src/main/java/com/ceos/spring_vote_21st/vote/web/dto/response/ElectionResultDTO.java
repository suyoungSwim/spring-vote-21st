package com.ceos.spring_vote_21st.vote.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class ElectionResultDTO {
    private ElectionResponseDTO election;
    private List<VoteCount4CandidateDTO> voteCounts;

    public static ElectionResultDTO from(ElectionResponseDTO electionResponseDTO, List<VoteCount4CandidateDTO> voteCounts) {
        return ElectionResultDTO.builder()
                .election(electionResponseDTO)
                .voteCounts(voteCounts)
                .build();
    }
}
