package com.ceos.spring_vote_21st.vote.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ElectionStatus {
    PREPARING("선거 전"), ONGOING("선거 중"), CLOSED("선거 완료");

    private final String value;
}
