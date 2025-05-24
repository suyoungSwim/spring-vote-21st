package com.ceos.spring_vote_21st.vote.web.dto;

// ElectionRequestDTO.java

import com.ceos.spring_vote_21st.vote.domain.ElectionStatus;
import com.ceos.spring_vote_21st.vote.domain.Section;
import lombok.Builder;
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

    private List<CandidateCreateRequestDTO> candidates = new ArrayList<>();
}

