package com.ceos.spring_vote_21st.vote.web.dto;


import com.ceos.spring_vote_21st.vote.domain.Candidate;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@Getter
public class CandidateWithVoteResponseDTO {
    private Long id;
    private String name;
    private Long electionId;
    private int voteCount;

    public static CandidateWithVoteResponseDTO from(Candidate candidate, int voteCount) {
        return CandidateWithVoteResponseDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .electionId(candidate.getElection().getId())
                .voteCount(voteCount)
                .build();
    }
}
