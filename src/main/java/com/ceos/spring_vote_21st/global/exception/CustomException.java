package com.ceos.spring_vote_21st.global.exception;

import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private ServiceCode serviceCode;

    public CustomException(ServiceCode serviceCode, Throwable cause) {
        super(cause);
        this.serviceCode = serviceCode;
    }

    public CustomException(ServiceCode serviceCode) {
        this.serviceCode = serviceCode;
    }
}
