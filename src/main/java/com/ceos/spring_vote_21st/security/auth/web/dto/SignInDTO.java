package com.ceos.spring_vote_21st.security.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor
@Getter
@NoArgsConstructor
public class SignInDTO {
    private String username;
    private String password;
}
