package com.eiv.exceptions;

public class DataIntegrityViolationServiceException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public DataIntegrityViolationServiceException(String message, Object... args) {
        super(message, args);
    }
    
    public int getCode() {
        return ServiceException.SERVICE_EXCEPTION_DATA_INTEGRITY_VIOLATION;
    }
}
