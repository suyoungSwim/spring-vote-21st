package com.ceos.spring_vote_21st.vote.domain;

import com.ceos.spring_vote_21st.global.domain.BaseEntity;
import com.ceos.spring_vote_21st.vote.domain.enums.ElectionStatus;
import com.ceos.spring_vote_21st.vote.domain.enums.Section;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Election extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(STRING)
    private ElectionStatus electionStatus;

    @Enumerated(STRING)
    private Section section;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    /** Vote는 단독 조회 필요*/
    @OneToMany(mappedBy = "election")
    @Builder.Default
    private List<Vote> votes = new ArrayList<>();

    /** 양방향 연관관계는 지양하기로 했으나, Candidate는 단독조회하는 경우가 잘 없으며 Election과 영속성 상태를 같이 가져가는 것이 자연스러워 양방향 cascade를 택함 */
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Candidate> candidates = new ArrayList<>();

    //연관관계 편의 메서드
    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }

}
