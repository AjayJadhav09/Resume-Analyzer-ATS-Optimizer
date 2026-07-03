package com.ajay.resume_analyzer.service.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
public class DocxResumeParser implements ResumeParser {

    @Override
    public String extractText(File file) throws IOException {

        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            StringBuilder text = new StringBuilder();

            document.getParagraphs().forEach(paragraph ->
                    text.append(paragraph.getText())
                            .append(System.lineSeparator()));

            return text.toString();
        }
    }
}