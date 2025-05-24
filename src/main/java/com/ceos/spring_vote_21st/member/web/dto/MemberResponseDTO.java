package com.ceos.spring_vote_21st.member.web.dto;

import com.ceos.spring_vote_21st.member.domain.CeosPosition;
import com.ceos.spring_vote_21st.member.domain.CeosTeam;
import com.ceos.spring_vote_21st.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private CeosPosition position;
    private CeosTeam team;
    //password는 전송안함

    public static MemberResponseDTO from(Member member) {
        return MemberResponseDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .username(member.getUsername())
                .email(member.getEmail())
                .position(member.getPosition())
                .team(member.getTeam())
                .build();
    }
}
