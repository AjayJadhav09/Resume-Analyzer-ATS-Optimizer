package com.ajay.resume_analyzer.service.resume;

import com.ajay.resume_analyzer.dto.response.ResumeResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    ResumeResponse uploadResume(MultipartFile file, Authentication authentication);
}
