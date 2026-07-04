package com.ajay.resume_analyzer.ai.controller;

import com.ajay.resume_analyzer.ai.dto.ResumeAnalysisResponse;
import com.ajay.resume_analyzer.ai.service.ResumeAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class ResumeAnalysisController {

    private final ResumeAnalysisService resumeAnalysisService;

    @PostMapping("/analyze/{resumeId}")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(
            @PathVariable Long resumeId) {

        ResumeAnalysisResponse response =
                resumeAnalysisService.analyzeResume(resumeId);

        return ResponseEntity.ok(response);
    }
}