package com.ajay.resume_analyzer.service.parser;

import java.io.File;
import java.io.IOException;

public interface ResumeParser {

    String extractText(File file) throws IOException;

}