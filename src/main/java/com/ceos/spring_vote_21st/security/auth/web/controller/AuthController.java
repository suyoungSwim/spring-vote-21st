package com.ceos.spring_vote_21st.security.auth.web.controller;

import com.ceos.spring_vote_21st.global.response.dto.CommonResponse;
import com.ceos.spring_vote_21st.member.service.MemberService;
import com.ceos.spring_vote_21st.member.web.dto.MemberResponseDTO;
import com.ceos.spring_vote_21st.security.auth.application.jwt.JwtTokenProvider;
import com.ceos.spring_vote_21st.security.auth.application.jwt.refresh.RefreshTokenService;
import com.ceos.spring_vote_21st.security.auth.application.service.AuthService;
import com.ceos.spring_vote_21st.security.auth.web.dto.SignUpDTO;
import io.netty.handler.ssl.PemPrivateKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 경로가 원래는 /api/v1/auth/**가 맞으나 기존 프론트팀의 코드수정 최소화를 위해 해당프로젝트에서만 /auth => /users 사용
 *
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody SignUpDTO dto) {
        Long id = authService.signUp(dto);

        return ResponseEntity.ok(id);
    }

    // 로그인 및 토큰유지: Spring Security

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessHeader, @CookieValue("refreshToken") String refreshToken) {
        log.info("refreshToken: " + refreshToken);
        authService.logout(accessHeader, refreshToken);

        return ResponseEntity.ok().build();
    }

    // 아이디 중복 확인
    @GetMapping("/signup/username/exists")
    public ResponseEntity<CommonResponse<?>> isDuplicateUsername(@RequestParam String username) {
        boolean exists = authService.isUsernameExists(username);

        return ResponseEntity.ok(CommonResponse.success(Map.of("exists", exists)));
    }

    // 이메일 중복 확인
    @GetMapping("/signup/email/exists")
    public ResponseEntity<CommonResponse<?>> isDuplicateEmail(@RequestParam String email) {
        boolean exists = authService.isEmailExists(email);

        return ResponseEntity.ok(CommonResponse.success(Map.of("exists", exists)));
    }
}