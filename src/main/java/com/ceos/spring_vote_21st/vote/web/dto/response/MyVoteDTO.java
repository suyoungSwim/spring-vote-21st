package com.ceos.spring_vote_21st.vote.web.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@Getter
public class MyVoteDTO {
    private boolean isVoted;
    private CandidateResponseDTO candidate;

    public static MyVoteDTO create(boolean isVoted, CandidateResponseDTO candidateResponseDTO) {
        return MyVoteDTO.builder()
                .isVoted(isVoted)
                .candidate(candidateResponseDTO)
                .build();
    }
}
