package com.ceos.spring_vote_21st.security.auth.application.service;

import com.ceos.spring_vote_21st.global.exception.CustomException;
import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.member.domain.Member;
import com.ceos.spring_vote_21st.member.domain.Role;
import com.ceos.spring_vote_21st.member.repository.MemberRepository;
import com.ceos.spring_vote_21st.security.auth.application.jwt.JwtTokenProvider;
import com.ceos.spring_vote_21st.security.auth.application.jwt.blacklist.BlacklistTokenService;
import com.ceos.spring_vote_21st.security.auth.application.jwt.refresh.RefreshTokenService;
import com.ceos.spring_vote_21st.security.auth.web.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final BlacklistTokenService blacklistTokenService;

    //create(signup)
    @Transactional
    public Long signUp(SignUpDTO dto) {
        // id  중복 체크
        String username = dto.getUsername();
        if (isUsernameExists(username)) {
            throw new CustomException(ServiceCode.USERNAME_ALREADY_EXISTS);
        }

        // email 중복 체크
        if (isEmailExists(dto.getEmail())) {
            throw new CustomException(ServiceCode.EMAIL_ALREADY_EXISTS);
        }

        Member entity = Member.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .team(dto.getTeam())
                .email(dto.getEmail())
                .position(dto.getPosition())
                .build();
        entity.getRoles().add(Role.ROLE_USER);

        return memberRepository.save(entity).getId();
    }

    public boolean isEmailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isUsernameExists(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }



/*
    public String signIn(SignInDTO dto) {
        User findUser = userRepository.findByUsername(dto.getUsername()).orElseThrow();

        // 비번 검증
        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new CustomJisikInException(ErrorCode.SIGNIN_FAILED);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(findUser.getId());


        return accessToken;
    }
*/

    /*
     */
/*
   accessToken: 헤더에서 취함
   refreshToken: body에서 취함
   *//*

    public ReissueResponseDTO reissue(String accessHeader, String refreshToken) {
        String accessToken = accessHeader.replace("Bearer ", "");

        // refresh token 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomJisikInException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 본인 refresh token인지 검증
        Long userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        String savedRefreshToken = refreshTokenService.getToken(userId);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new CustomJisikInException(ErrorCode.TOKEN_MISMATCH);
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(userId, jwtTokenProvider.getUsernameFromToken(accessToken));

        return new ReissueResponseDTO(newAccessToken);
    }
*/

    // TODO: logouthandler로 등록해보자
    // 로그아웃: 블랙리스트 등록 & refreshToken삭제
    public void logout(String accessHeader, String refreshToken) {
        String accessToken = accessHeader.replace("Bearer ", "");
        // 토큰 검증

        // case1: 만료된 토큰
        if (!jwtTokenProvider.validateToken(accessToken)) {
            // 1. refresh token 검증
            jwtTokenProvider.validateToken(refreshToken);
            // 2. access token 소유자 검증
            jwtTokenProvider.validateTokenOwnership(accessToken, refreshToken);
            log.info("만료된 토큰이었음에도 로그아웃 잘 됐음");
        } //case2: 유효한 토큰
        else {
            // accessToken을 블랙리스트로 등록
            long remainExpiration = jwtTokenProvider.getRemainExpiration(accessToken);
            blacklistTokenService.blacklistAccessToken(accessToken, remainExpiration);
        }
        // refreshToken 삭제
        Long userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        //TODO: refreshToken은 검증을 전혀 안하고 있음. refreshToken 유효성 검증이 필요. & accessToken,refreshToken모두 무효화하게 되므로 둘의 소유자 일치 여부도 필요
        refreshTokenService.deleteToken(userId);
    }

}
