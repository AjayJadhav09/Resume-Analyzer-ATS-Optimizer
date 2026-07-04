package com.ajay.resume_analyzer.exception;

public class ResumeFileNotFoundException extends RuntimeException {

    public ResumeFileNotFoundException(String filePath) {
        super("Resume file not found: " + filePath);
    }
}