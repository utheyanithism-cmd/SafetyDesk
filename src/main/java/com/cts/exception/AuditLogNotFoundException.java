package com.cts.exception;

public class AuditLogNotFoundException extends RuntimeException {

    public AuditLogNotFoundException(String message) {
        super(message);
    }
}