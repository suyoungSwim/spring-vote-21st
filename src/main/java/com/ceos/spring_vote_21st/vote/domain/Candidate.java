package com.ceos.spring_vote_21st.vote.domain;

import com.ceos.spring_vote_21st.member.domain.CeosTeam;
import com.ceos.spring_vote_21st.vote.web.dto.request.CandidateModifyRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder(access = PRIVATE)
@Entity
public class Candidate {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    private Election election;

    private String name;

    private CeosTeam team;

    public static Candidate create(Election election, String name, CeosTeam team) {
        Candidate candidate = Candidate.builder()
                .election(election)
                .name(name)
                .team(team)
                .build();
        election.addCandidate(candidate);

        return candidate;
    }
    public void update(CandidateModifyRequestDTO dto) {
        this.name = dto.getName();
    }
}

// 중복 출마 불가능
