package com.ceos.spring_vote_21st.vote.web.dto.request;

import com.ceos.spring_vote_21st.member.domain.CeosTeam;
import com.ceos.spring_vote_21st.vote.domain.Election;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateCreateRequestDTO {
    private String name;

    private CeosTeam team;
}
