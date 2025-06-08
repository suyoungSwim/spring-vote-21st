package com.ceos.spring_vote_21st.vote.web.dto.response;


import com.ceos.spring_vote_21st.vote.domain.Candidate;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@Getter
public class CandidateWithVoteResponseDTO {
    private Long electionId;
    private Long candidateId;
    private String name;
    private int voteCount;

    public static CandidateWithVoteResponseDTO from(Candidate candidate, int voteCount) {
        return CandidateWithVoteResponseDTO.builder()
                .electionId(candidate.getElection().getId())
                .candidateId(candidate.getId())
                .name(candidate.getName())
                .voteCount(voteCount)
                .build();
    }
}
