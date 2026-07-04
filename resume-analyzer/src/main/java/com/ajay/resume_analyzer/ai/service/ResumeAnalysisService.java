package com.ajay.resume_analyzer.ai.service;

import com.ajay.resume_analyzer.ai.dto.ResumeAnalysisResponse;

public interface ResumeAnalysisService {
    ResumeAnalysisResponse analyzeResume(Long resumeId);
}
