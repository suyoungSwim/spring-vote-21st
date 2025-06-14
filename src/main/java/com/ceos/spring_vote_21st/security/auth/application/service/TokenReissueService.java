package com.ceos.spring_vote_21st.security.auth.application.service;

import com.ceos.spring_vote_21st.global.exception.CustomException;
import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.security.auth.application.jwt.JwtTokenProvider;
import com.ceos.spring_vote_21st.security.auth.application.jwt.refresh.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenReissueService {
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider tokenProvider;
    private final JwtTokenProvider jwtTokenProvider;

    public String reissueAccessToken(String refreshToken) {
        Long userIdFromToken = tokenProvider.getUserIdFromToken(refreshToken);
        String usernameFromToken = tokenProvider.getUsernameFromToken(refreshToken);

        // rotation token 미적용
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ServiceCode.INVALID_TOKEN);
        }

        // 서버에 저장된 refreshToken과 비교
        String savedRefreshToken = refreshTokenService.getToken(userIdFromToken);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new CustomException(ServiceCode.TOKEN_MISMATCH);
        }

        // refreshToken이 validated & 서버의 refreshToken과 일치
        return tokenProvider.generateAccessToken(userIdFromToken, usernameFromToken);
    }

}
