package com.ceos.spring_vote_21st.vote.web.dto.request;

import com.ceos.spring_vote_21st.vote.domain.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteCreateRequestDTO {
    private Long electionId;
    private Long candidateId;
}
