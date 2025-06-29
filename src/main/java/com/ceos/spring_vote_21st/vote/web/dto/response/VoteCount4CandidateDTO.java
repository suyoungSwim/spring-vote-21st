package com.ceos.spring_vote_21st.vote.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VoteCount4CandidateDTO {
    private long electionId;
    private String candidateName;
    private long candidateId;
    private long voteCount;

    public VoteCount4CandidateDTO(long electionId, String candidateName, long candidateId, long voteCount) {
        this.electionId = electionId;
        this.candidateName = candidateName;
        this.candidateId = candidateId;
        this.voteCount = voteCount;
    }
}
