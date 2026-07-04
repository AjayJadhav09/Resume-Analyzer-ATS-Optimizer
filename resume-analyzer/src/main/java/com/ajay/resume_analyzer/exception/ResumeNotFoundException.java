package com.ajay.resume_analyzer.exception;

public class ResumeNotFoundException extends RuntimeException {

    public ResumeNotFoundException(Long id) {
        super("Resume not found with id : " + id);
    }

}