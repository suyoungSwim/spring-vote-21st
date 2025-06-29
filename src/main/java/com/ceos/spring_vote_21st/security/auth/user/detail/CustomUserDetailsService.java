package com.ceos.spring_vote_21st.security.auth.user.detail;

import com.ceos.spring_vote_21st.global.exception.CustomException;
import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.member.domain.Member;
import com.ceos.spring_vote_21st.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    // UsernamePasswordAuthenticationFilter도 pw를 가져오기위해 아래 함수 사용
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() ->
                {
                    log.warn(ServiceCode.MEMBER_NOT_EXISTS.getMessage() + "username: {}", username);
                    return new CustomException(ServiceCode.MEMBER_NOT_EXISTS);
                }
                );

        return CustomUserDetails.from(member);
    }
}
