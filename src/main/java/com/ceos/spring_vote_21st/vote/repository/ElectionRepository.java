package com.ceos.spring_vote_21st.vote.repository;

import com.ceos.spring_vote_21st.vote.domain.Election;
import com.ceos.spring_vote_21st.vote.domain.enums.Section;
import io.lettuce.core.ScanIterator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    List<Election> findBySection(Section section);
}
