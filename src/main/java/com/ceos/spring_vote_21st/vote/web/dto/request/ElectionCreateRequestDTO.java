package com.ceos.spring_vote_21st.vote.web.dto.request;

// ElectionRequestDTO.java

import com.ceos.spring_vote_21st.vote.domain.enums.ElectionStatus;
import com.ceos.spring_vote_21st.vote.domain.enums.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ElectionCreateRequestDTO {
    private String name;

    private ElectionStatus electionStatus;

    private Section section;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private List<CandidateCreateRequestDTO> candidates = new ArrayList<>();
}

