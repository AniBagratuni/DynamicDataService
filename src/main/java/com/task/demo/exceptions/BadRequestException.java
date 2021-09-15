package com.task.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        this(message, (Object) null);
    }

    public BadRequestException(String message, Object errorContent) {
        this(message, (Throwable) null);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
