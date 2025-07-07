package com.example.AMS.exception;

public class H_AssetException extends RuntimeException {
    public H_AssetException(String message) {
        super(message);
    }

    public H_AssetException(String message, Throwable cause) {
        super(message, cause);
    }

    public H_AssetException(Throwable cause) {
        super(cause);
    }
}
