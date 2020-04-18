package com.template.comon.exception;


public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3152616724785436891L;

    public BusinessException(String frdMessage) {
        super(frdMessage);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(Throwable throwable, String frdMessage) {
        super(frdMessage, throwable);
    }
}