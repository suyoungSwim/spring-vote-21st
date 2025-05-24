package com.ceos.spring_vote_21st.vote.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CandidateModifyRequestDTO {
    private Long electionId;

    private Long candidateId;

    private String name;
}
