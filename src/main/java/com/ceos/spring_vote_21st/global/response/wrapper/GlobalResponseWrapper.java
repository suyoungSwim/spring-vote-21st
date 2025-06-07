package com.ceos.spring_vote_21st.global.response.wrapper;

import com.ceos.spring_vote_21st.global.response.domain.ServiceCode;
import com.ceos.spring_vote_21st.global.response.dto.CommonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/*
* 기능: 응답의 body만 wrapping함.
* 그 외의 status, header를 건드는건 목적이 아님
* */
@Order(2)
@RestControllerAdvice
public class GlobalResponseWrapper implements ResponseBodyAdvice {
    // Wrapping 대상
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true; // 모두
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // body가 이미 CommonResponse
        if (body instanceof CommonResponse) {
            return body;
        }
        //swagger 관련은 wrapping 안함
        String path = request.getURI().getPath();
        if (path.contains("swagger")
                || path.contains("api-docs")
                || path.contains("webjars")) {
            return body;
        }

        // CommonResponse 생성
        CommonResponse<Object> commonResponse;
        ServiceCode serviceCode = getServiceCode((ServletServerHttpResponse) response);
        if (serviceCode.equals(ServiceCode.SUCCESS)) {
            commonResponse = CommonResponse.success(body);
        } else {
            commonResponse = CommonResponse.failure(body);   // 원래 Exception은 여기까지 안옴: 전역예외 핸들러에서 CommonResponse로 변환 되었어야했음.
        }


        // case1: body가 String인 경우에는 String으로 재변환 필요
        if (body instanceof String) {
            return convertToStringResponse(commonResponse);
        }

        // case2: 그 외는 CommonResponse 그대로 반환
        return commonResponse;
    }

    private String convertToStringResponse(CommonResponse<Object> commonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 문자열 형태로 변환 → String 타입으로 반환해야 converter 충돌이 없음
            return objectMapper.writeValueAsString(commonResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("String response conversion error", e);
        }
    }

    private ServiceCode getServiceCode(ServletServerHttpResponse response) {
        int status = response
                .getServletResponse()
                .getStatus();
        ServiceCode serviceCode = (status >= 200 && status < 300) ? ServiceCode.SUCCESS : ServiceCode.NOT_DEFINED_ERROR;
        return serviceCode;
    }

}
