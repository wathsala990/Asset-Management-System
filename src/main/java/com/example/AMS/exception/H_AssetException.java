package com.example.AMS.exception;

/**
 * Custom exception for asset-related errors.
 * This allows for more specific error handling in the service and controller layers.
 */
public class H_AssetException extends RuntimeException {

    /**
     * Constructs a new H_AssetException with the specified detail message.
     * @param message the detail message.
     */
    public H_AssetException(String message) {
        super(message);
    }

    /**
     * Constructs a new H_AssetException with the specified detail message and cause.
     * @param message the detail message.
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public H_AssetException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new H_AssetException with the specified cause.
     * @param cause the cause.
     */
    public H_AssetException(Throwable cause) {
        super(cause);
    }
}
