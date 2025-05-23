package com.ceos.spring_vote_21st.vote.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Election {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(STRING)
    private ElectionStatus electionStatus;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;
}
