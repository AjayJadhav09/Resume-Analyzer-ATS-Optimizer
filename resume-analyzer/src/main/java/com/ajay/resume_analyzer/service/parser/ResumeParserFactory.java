package com.ajay.resume_analyzer.service.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumeParserFactory {

    private final PdfResumeParser pdfResumeParser;
    private final DocxResumeParser docxResumeParser;

    public ResumeParser getParser(String contentType) {

        return switch (contentType) {

            case "application/pdf" ->
                    pdfResumeParser;

            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ->
                    docxResumeParser;

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported file type: " + contentType);
        };

    }

}