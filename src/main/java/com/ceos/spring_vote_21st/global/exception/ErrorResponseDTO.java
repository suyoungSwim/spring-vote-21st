package com.ceos.spring_vote_21st.global.exception;

import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ErrorResponseDTO {
    private int statusCode;
    private String message;
    private String codeName;

    public static ErrorResponseDTO of(ServiceCode serviceCode) {
        return ErrorResponseDTO.builder()
                .statusCode(serviceCode.getHttpStatus().value())
                .message(serviceCode.getMessage())
                .codeName(serviceCode.name())
                .build();
    }
}
