package com.ajay.resume_analyzer.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResumeResponse {
    private Long id;
    private String fileName;
    private String originalFileName;
    private Long fileSize; // Added for Improvement 5
    private String message;
}