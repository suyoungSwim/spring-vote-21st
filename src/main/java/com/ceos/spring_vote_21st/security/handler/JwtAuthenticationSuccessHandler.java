package com.ceos.spring_vote_21st.security.handler;

import com.ceos.spring_vote_21st.security.auth.application.jwt.JwtTokenProvider;
import com.ceos.spring_vote_21st.security.auth.application.jwt.refresh.RefreshTokenService;
import com.ceos.spring_vote_21st.security.auth.user.detail.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = tokenProvider.generateAccessToken(userDetails.getUserId(), userDetails.getUsername());
        String refreshToken = tokenProvider.generateRefreshToken(userDetails.getUserId(), userDetails.getUsername());


        // accessToken 헤더 담기
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken);

        //refreshToken 서버 저장
        refreshService.saveToken(userDetails.getUserId(), refreshToken);
        //refershToken 쿠키 담기
        long cookieAge = tokenProvider.getJwtProperties().getRefreshTokenExpiration() / 1000;   // 초 단위
        String refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)   //필수
                .sameSite("None") //필수, CORS가 도메인이 달라도 쿠키전송 & secure 필수
                .secure(true)     //필수, https필수 -> 이것들을 해야 쿠키가 브라우저에 저장되고 전송됨
                .path("/")
                .maxAge(cookieAge)
//                .domain("hanihome-api.dev")
                .build()
                .toString();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie);

/**
 * response.setHeader(), addHeader() 차이
* 단일 값 헤더(Authorization, Content-Type 등)는 보통 setHeader
 * 멀티 값 헤더(Set-Cookie, Cache-Control 등)나 같은 이름으로 여러 값을 남기고 싶을 땐 addHeader
* */
        // 5) 상태 코드
        response.setStatus(HttpStatus.CREATED.value());
    }
}
