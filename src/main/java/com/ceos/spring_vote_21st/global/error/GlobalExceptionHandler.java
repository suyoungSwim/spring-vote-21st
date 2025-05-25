package com.ceos.spring_vote_21st.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("CustomException 발생: 상태코드={}, 메시지={}, 에러명={}",
                errorCode.getHttpStatus().value(),
                errorCode.getMessage(),
                errorCode.name());

        return ResponseEntity.status(errorCode.getHttpStatus().value())
                .body(ErrorResponseEntity.of(errorCode));
    }
}
