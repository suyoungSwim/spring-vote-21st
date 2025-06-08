package com.ceos.spring_vote_21st.vote.web.dto.request;

import com.ceos.spring_vote_21st.member.domain.CeosTeam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateAddRequestDTO {
        private Long electionId;

        private String name;

        private CeosTeam team;

}
