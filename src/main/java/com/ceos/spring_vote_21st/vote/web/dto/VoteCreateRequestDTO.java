package com.ceos.spring_vote_21st.vote.web.dto;

import com.ceos.spring_vote_21st.vote.domain.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteCreateRequestDTO {
    private Long electionId;
    private Long memberId;
    private Long candidateId;
}
