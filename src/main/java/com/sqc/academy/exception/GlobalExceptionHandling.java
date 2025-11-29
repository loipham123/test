package com.sqc.academy.exception;

import com.sqc.academy.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(ApiException.class)
    public ApiResponse<String> handleApiException(ApiException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex) {
        ex.printStackTrace();
        return ApiResponse.error("Có lỗi xảy ra: " + ex.getMessage());
    }
}
