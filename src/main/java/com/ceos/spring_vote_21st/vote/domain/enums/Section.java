package com.ceos.spring_vote_21st.vote.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Section {
    DEMO_DAY("데모데이 투표"), FRONT_KING("프론트엔드 파트장 투표"), BACK_KING("백엔드 파트장 투표");

    private final String value;
}
