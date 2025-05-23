package com.ceos.spring_vote_21st.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CeosTeam {
    POP_UPCYCLE("팝업사이클"),
    HANI_HOME("하니홈"),
    DEAR_DREAM("이어드림"),
    PROMESA("프로메사"),
    INFLUY("인플루이");

    private final String value;
}
