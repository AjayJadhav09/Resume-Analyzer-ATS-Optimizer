package com.ajay.resume_analyzer.exception;

public class InvalidResumeException extends RuntimeException {
    public InvalidResumeException(String message) {
        super(message);
    }
}