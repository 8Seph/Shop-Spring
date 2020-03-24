package ru.geekbrains.supershop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public InternalServiceException(String msg) {
        super(msg);
    }
}