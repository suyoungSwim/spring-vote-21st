package com.ceos.spring_vote_21st.security.auth.web.controller;

import com.ceos.spring_vote_21st.global.exception.CustomException;
import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.global.response.dto.CommonResponse;
import com.ceos.spring_vote_21st.member.service.MemberService;
import com.ceos.spring_vote_21st.member.web.dto.MemberResponseDTO;
import com.ceos.spring_vote_21st.security.auth.application.jwt.JwtTokenProvider;
import com.ceos.spring_vote_21st.security.auth.application.jwt.refresh.RefreshTokenService;
import com.ceos.spring_vote_21st.security.auth.application.service.AuthService;
import com.ceos.spring_vote_21st.security.auth.application.service.TokenReissueService;
import com.ceos.spring_vote_21st.security.auth.web.dto.SignUpDTO;
import io.netty.handler.ssl.PemPrivateKey;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final AuthService authService;
    private final TokenReissueService tokenReissueService;

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

    // 액세스 토큰 재발급
    @PostMapping("/tokens/refresh")
    public ResponseEntity<CommonResponse<?>> reissueAccessToken(@CookieValue("refreshToken") String refreshToken) {
        String reissueAccessToken = tokenReissueService.reissueAccessToken(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissueAccessToken)
                .body(CommonResponse.success(null));
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