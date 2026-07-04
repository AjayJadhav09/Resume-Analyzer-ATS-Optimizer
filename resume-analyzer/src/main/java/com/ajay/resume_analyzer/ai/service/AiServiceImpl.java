package com.ajay.resume_analyzer.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final ChatClient chatClient;

    @Override
    public String askGemini(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}