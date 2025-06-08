package com.ceos.spring_vote_21st.vote.web.dto.response;


import com.ceos.spring_vote_21st.vote.domain.Election;
import com.ceos.spring_vote_21st.vote.domain.enums.ElectionStatus;
import com.ceos.spring_vote_21st.vote.domain.enums.Section;
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
    private Section section;

    public static ElectionResponseDTO from(Election e) {
        return ElectionResponseDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .electionStatus(e.getElectionStatus())
                .startedAt(e.getStartedAt())
                .finishedAt(e.getFinishedAt())
                .section(e.getSection())
                .build();
    }
}

