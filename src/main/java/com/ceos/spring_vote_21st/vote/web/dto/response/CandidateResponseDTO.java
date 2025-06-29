package com.ceos.spring_vote_21st.vote.web.dto.response;

import com.ceos.spring_vote_21st.member.domain.CeosTeam;
import com.ceos.spring_vote_21st.vote.domain.Candidate;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@NoArgsConstructor
public class CandidateResponseDTO {
    private Long id;
    private String name;
    private Long electionId;
    private CeosTeam team;

    public static CandidateResponseDTO from(Candidate entity) {
        return CandidateResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .electionId(entity.getElection().getId())
                .team(entity.getTeam())
                .build();
    }

}