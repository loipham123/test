package com.sqc.academy.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiException extends RuntimeException {
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }
}
