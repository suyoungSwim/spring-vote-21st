package com.ceos.spring_vote_21st.member.web.dto;

import com.ceos.spring_vote_21st.member.domain.CeosPosition;
import com.ceos.spring_vote_21st.member.domain.CeosTeam;
import com.ceos.spring_vote_21st.member.domain.Member;

public class MemberCreateRequestDTO {

    private String name;

    private String username;

    private String password;

    private String email;

    private CeosPosition position;

    private CeosTeam team;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .username(username)
                .password(password)
                .email(email)
                .position(position)
                .team(team)
                .build();
    }
}
