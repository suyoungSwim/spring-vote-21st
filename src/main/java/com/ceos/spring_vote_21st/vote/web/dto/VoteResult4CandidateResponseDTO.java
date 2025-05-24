package com.ceos.spring_vote_21st.vote.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class VoteResult4CandidateResponseDTO {
    private long electionId;
    private String candidateName;
    private int voteCount;
}
