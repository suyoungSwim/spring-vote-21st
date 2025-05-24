package com.ceos.spring_vote_21st.vote.web.dto;


import com.ceos.spring_vote_21st.vote.domain.Election;
import com.ceos.spring_vote_21st.vote.domain.ElectionStatus;
import com.ceos.spring_vote_21st.vote.domain.Section;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ElectionResponseDTO {
    private Long id;
    private String name;
    private ElectionStatus electionStatus;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private List<CandidateResponseDTO> candidates;
    private Section section;

    public static ElectionResponseDTO from(Election e) {
        return ElectionResponseDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .electionStatus(e.getElectionStatus())
                .startedAt(e.getStartedAt())
                .finishedAt(e.getFinishedAt())
                .candidates(e.getCandidates().stream()
                        .map(c -> CandidateResponseDTO.builder()
                                .electionId(e.getId())
                                .name(c.getName())
                                .id(c.getId())
                                .build())
                        .collect(Collectors.toList()))
                .section(e.getSection())
                .build();
    }
}

