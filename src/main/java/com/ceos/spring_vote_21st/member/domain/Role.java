package com.ceos.spring_vote_21st.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("USER","사용자"), ROLE_ADMIN("ADMIN", "관리자");

    private final String key;
    private final String value;
}
