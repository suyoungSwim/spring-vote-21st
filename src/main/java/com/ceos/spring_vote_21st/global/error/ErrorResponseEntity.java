package com.ceos.spring_vote_21st.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Builder
public class ErrorResponseEntity {
    private int statusCode;
    private String message;
    private String codeName;
    public static ErrorResponseEntity of(ErrorCode errorCode) {
        return ErrorResponseEntity.builder()
                .statusCode(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .codeName(errorCode.name())
                .build();
    }
}
