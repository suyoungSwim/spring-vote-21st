package com.ceos.spring_vote_21st.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * CORS 전역 설정
 * - Authorization 헤더를 클라이언트에 노출하여 Access Token 볼 수 있도록 지원함
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 허용할 오리진 패턴 (필요 시 특정 도메인만 허용)
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "https://next-vote-21th.vercel.app",
            "https://hanihome-fe-test-gnos-projects-ab4a3758.vercel.app"
        ));
        // 허용할 HTTP 메서드
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // 허용할 요청 헤더
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization")); // 클라이언트가 AccessToken 접근 가능하도록 지원
        // 쿠키 전송 허용 여부
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);    // 모든 URI에 대해서
        return source;
    }
    /*
     *
       @Bean
    @Profile("local")              // 로컬 프로필
    public CorsConfigurationSource devCorsConfig() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "https://*.ngrok-free.app"
        ));
        c.setAllowCredentials(false);
        applyCommon(c);
        return source(c);
    }

    @Bean
    @Profile("prod")               // 운영 프로필
    public CorsConfigurationSource prodCorsConfig() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOriginPatterns(List.of(
                "https://web.example.com",
                "https://admin.example.com"
        ));
        c.setAllowCredentials(true);
        applyCommon(c);
        return source(c);
    }
     */
}
