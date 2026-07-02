package com.ajay.resume_analyzer.service.resume;

import com.ajay.resume_analyzer.dto.response.ResumeResponse;
import com.ajay.resume_analyzer.entity.Resume;
import com.ajay.resume_analyzer.entity.User;
import com.ajay.resume_analyzer.exception.InvalidResumeException;
import com.ajay.resume_analyzer.repository.ResumeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    private final ResumeRepository resumeRepository;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(uploadDir).toAbsolutePath().normalize());
            log.info("Upload directory initialized at: {}", uploadDir);
        } catch (IOException e) {
            log.error("Could not create upload directory", e);
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @Override
    public ResumeResponse uploadResume(MultipartFile file, Authentication authentication) {

        // 1. Check for empty files
        if (file.isEmpty()) {
            log.warn("Upload failed: File is empty.");
            throw new InvalidResumeException("Uploaded file is empty.");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "");

        // 2. Comprehensive Validation
        validateFile(originalFileName, file.getContentType());

        try {
            int index = originalFileName.lastIndexOf(".");
            String extension = originalFileName.substring(index);
            String fileName = UUID.randomUUID() + extension;

            // 4. Use Path.of()
            Path targetLocation = Path.of(uploadDir).toAbsolutePath().normalize().resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            User user = (User) authentication.getPrincipal();

            Resume resume = Resume.builder()
                    .fileName(fileName)
                    .originalFileName(originalFileName)
                    .fileType(file.getContentType())
                    .filePath(targetLocation.toString())
                    .user(user)
                    .build();

            resume = resumeRepository.save(resume);

            // 6. Logging success
            log.info("Resume uploaded successfully: {} by user: {}", fileName, user.getUsername());

            return ResumeResponse.builder()
                    .id(resume.getId())
                    .fileName(fileName)
                    .originalFileName(originalFileName)
                    .fileSize(file.getSize()) // 5. Return file size
                    .message("Resume uploaded successfully")
                    .build();

        } catch (IOException e) {
            // 6. Logging error
            log.error("Failed to upload resume: {}", originalFileName, e);
            throw new RuntimeException("Failed to upload resume", e);
        }
    }

    private void validateFile(String fileName, String contentType) {
        if (fileName.isEmpty()) {
            log.warn("Upload failed: File name is missing.");
            throw new InvalidResumeException("File name is missing or invalid.");
        }

        if (fileName.contains("..")) {
            log.warn("Upload failed: Filename contains invalid path sequence: {}", fileName);
            throw new InvalidResumeException("Filename contains invalid path sequence.");
        }

        // Validate Content Type
        if (!"application/pdf".equals(contentType)
                && !"application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
            log.warn("Upload failed: Invalid content type: {}", contentType);
            throw new InvalidResumeException("Invalid file type. Only PDF and DOCX are allowed.");
        }

        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            log.warn("Upload failed: No extension found in {}", fileName);
            throw new InvalidResumeException("Invalid file: No extension found.");
        }

        String extension = fileName.substring(index).toLowerCase();
        if (!extension.equals(".pdf") && !extension.equals(".docx")) {
            log.warn("Upload failed: Invalid file extension: {}", extension);
            throw new InvalidResumeException("Only PDF and DOCX files are allowed.");
        }
    }
}