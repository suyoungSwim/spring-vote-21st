package com.ceos.spring_vote_21st.admin.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/api/v1/cookie/create")
    public String createCookie(HttpServletResponse response) {
        String cookieName = "testCookie";
        String cookieValue = "testCookieValue";
        String testCookie = ResponseCookie.from(cookieName, cookieValue)
                .httpOnly(true)                  // JS에서 접근 불가
                .secure(true)                   // HTTPS에서만 전송
                .path("/")                      // 전체 경로에서 사용 가능
                .sameSite("None")              // Cross-site 요청 허용
                .maxAge(60)              // 쿠키 유효시간 설정 (초 단위)
                .build()
                .toString();

        response.addHeader(HttpHeaders.SET_COOKIE, testCookie);
        return "OK";
    }

    @GetMapping("/api/v1/cookie/input")
    public String inputCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("received cookie from client: {}", cookie.getValue());
            }
        }
        return "NO COOKIE";

    }
}
