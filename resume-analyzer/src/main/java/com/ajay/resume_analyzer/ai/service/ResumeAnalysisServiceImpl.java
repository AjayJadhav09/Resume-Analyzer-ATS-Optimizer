package com.ajay.resume_analyzer.ai.service;

import com.ajay.resume_analyzer.ai.dto.ResumeAnalysisResponse;
import com.ajay.resume_analyzer.ai.prompt.ResumePromptBuilder;
import com.ajay.resume_analyzer.entity.Resume;
import com.ajay.resume_analyzer.exception.ResumeFileNotFoundException;
import com.ajay.resume_analyzer.exception.ResumeNotFoundException;
import com.ajay.resume_analyzer.repository.ResumeRepository;
import com.ajay.resume_analyzer.service.parser.ResumeParser;
import com.ajay.resume_analyzer.service.parser.ResumeParserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeAnalysisServiceImpl implements ResumeAnalysisService {

    private final ResumeRepository resumeRepository;
    private final ResumeParserFactory parserFactory;
    private final ChatClient chatClient;

    @Override
    public ResumeAnalysisResponse analyzeResume(Long resumeId) {

        // 1. Fetch Resume
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new ResumeNotFoundException(resumeId));

        // 2. Verify File Exists
        File file = new File(resume.getFilePath());

        if (!file.exists()) {
            throw new ResumeFileNotFoundException(resume.getFilePath());
        }

        try {

            // 3. Extract Resume Text
            ResumeParser parser =
                    parserFactory.getParser(resume.getFileType());

            String resumeText = parser.extractText(file);

            // 4. Build AI Prompt
            String prompt =
                    ResumePromptBuilder.buildPrompt(resumeText);

            // 5. Send to Gemini
            try {

                return chatClient
                        .prompt()
                        .user(prompt)
                        .call()
                        .entity(ResumeAnalysisResponse.class);

            } catch (Exception e) {

                throw new RuntimeException(
                        "Failed to analyze resume using Gemini.",
                        e
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to parse resume.",
                    e
            );
        }
    }
}