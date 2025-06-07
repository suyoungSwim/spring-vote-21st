package com.ceos.spring_vote_21st.security.auth.web.controller;

import com.ceos.spring_vote_21st.member.service.MemberService;
import com.ceos.spring_vote_21st.member.web.dto.MemberResponseDTO;
import com.ceos.spring_vote_21st.security.auth.application.jwt.JwtTokenProvider;
import com.ceos.spring_vote_21st.security.auth.application.jwt.refresh.RefreshTokenService;
import com.ceos.spring_vote_21st.security.auth.application.service.AuthService;
import com.ceos.spring_vote_21st.security.auth.web.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<Long> signUp(@RequestBody SignUpDTO dto) {
        Long id = authService.signUp(dto);

        return ResponseEntity.ok(id);
    }

    // 로그아웃
    @PostMapping("/users/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessHeader, @CookieValue("refreshToken") String refreshToken) {
        log.info("refreshToken: " + refreshToken);
        authService.logout(accessHeader, refreshToken);

        return ResponseEntity.ok().build();
    }

}