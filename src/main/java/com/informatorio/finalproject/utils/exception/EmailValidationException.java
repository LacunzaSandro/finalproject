package com.informatorio.finalproject.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmailValidationException extends RuntimeException{
        public EmailValidationException(String exception) {
            super(exception);
        }

}
