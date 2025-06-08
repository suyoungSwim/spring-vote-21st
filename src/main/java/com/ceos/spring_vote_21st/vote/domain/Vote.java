package com.ceos.spring_vote_21st.vote.domain;

import com.ceos.spring_vote_21st.global.domain.BaseEntity;
import com.ceos.spring_vote_21st.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;


@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Vote extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "member_id")
    private Member member;  // 유권자

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "candidate_id")
    private Candidate candidate;    //후보자

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "election_id")
    private Election election;

    public static Vote create(Member member, Candidate candidate, Election election) {
        Vote vote = Vote.builder()
                .member(member)
                .candidate(candidate)
                .election(election)
                .build();

        election.addVote(vote);

        return vote;
    }
}
