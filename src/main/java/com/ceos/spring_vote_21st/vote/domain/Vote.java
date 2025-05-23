package com.ceos.spring_vote_21st.vote.domain;

import com.ceos.spring_vote_21st.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;


@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Vote {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "election_id")
    private Election election;
}
