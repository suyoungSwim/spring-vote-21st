package com.ceos.spring_vote_21st.vote.repository;

import com.ceos.spring_vote_21st.vote.domain.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
