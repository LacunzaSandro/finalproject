package com.informatorio.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SimpleException extends RuntimeException {
    public SimpleException(String exception) {
        super(exception);
    }
}
