package com.ceos.spring_vote_21st.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CeosPosition {
    FRONTEND("프론트엔드"),
    BACKEND("백엔드"),
    DESIGN("디자인"),
    PRODUCT_MANAGER("기획");

    private final String value;
}
