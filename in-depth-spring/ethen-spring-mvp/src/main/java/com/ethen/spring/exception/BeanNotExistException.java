package com.ethen.spring.exception;

public class BeanNotExistException extends RuntimeException {
    public BeanNotExistException(String message) {
        super(message);
    }
}
