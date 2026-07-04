package com.ajay.resume_analyzer.ai.prompt;

public final class ResumePromptBuilder {

    private ResumePromptBuilder() {
    }

    public static String buildPrompt(String resumeText) {

        return """
                You are an experienced ATS Resume Analyzer.

                Analyze the following resume.

                Evaluate:

                - ATS score
                - Resume summary
                - Strengths
                - Weaknesses
                - Missing technical skills
                - Improvement suggestions
                - Recommended projects
                - Recommended certifications

                Resume:

                %s
                """.formatted(resumeText);

    }

}