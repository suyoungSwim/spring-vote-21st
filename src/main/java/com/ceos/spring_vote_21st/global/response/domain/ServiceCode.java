package com.ceos.spring_vote_21st.global.response.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceCode {

    /**
     * 성공
     * */
    SUCCESS(HttpStatus.OK,"정상 처리되었습니다."),

    /**
     * 실패
     * */
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다"),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 ID의 회원이 존재하지 않습니다."),
    ENTITY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 엔티티가 없습니다."),
    POSITION_NOT_MATCH(HttpStatus.BAD_REQUEST, "자신과 다른 파트에 투표했습니다"),
    CANNOT_VOTE_SAME_TEAM(HttpStatus.BAD_REQUEST, "자신의 팀에 투표할 수 없습니다"),
    DUPLICATE_VOTE(HttpStatus.BAD_REQUEST, "이미 해당 선거에 투표했습니다"),
    //jwt
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰 검증에 실패했습니다."),
    TOKEN_MISMATCH(HttpStatus.BAD_REQUEST, "연관된 토큰이 아닙니다"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효한 토큰이 아닙니다"),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "토큰의 구조가 올바르지 않습니다"),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "미지원하는 토큰입니다"),

    // not defined
    NOT_DEFINED_ERROR(HttpStatus.BAD_REQUEST, "정의 되지 않은 에러입니다"),
    TOKEN_LOGOUT(HttpStatus.BAD_REQUEST, "로그아웃한 사용자입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
