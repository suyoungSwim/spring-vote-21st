package com.ceos.spring_vote_21st.member.repository;

import com.ceos.spring_vote_21st.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
