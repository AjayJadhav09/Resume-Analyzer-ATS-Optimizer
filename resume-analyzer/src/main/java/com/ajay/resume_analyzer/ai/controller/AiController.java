package com.ajay.resume_analyzer.ai.controller;

import com.ajay.resume_analyzer.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/test")
    public String test() {

        return aiService.askGemini(
                "Reply with exactly: Spring AI is working successfully."
        );
    }
}