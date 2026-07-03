    package com.ajay.resume_analyzer.controller;

    import com.ajay.resume_analyzer.dto.response.ResumeResponse;
    import com.ajay.resume_analyzer.service.resume.ResumeService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    @RestController
    @RequestMapping("/api/resume")
    @RequiredArgsConstructor
    public class ResumeController {

        private final ResumeService resumeService;

        @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<ResumeResponse> uploadResume(
                @RequestParam("file") MultipartFile file,
                Authentication authentication) {

            ResumeResponse response =
                    resumeService.uploadResume(file, authentication);

            return ResponseEntity.ok(response);
        }


        @GetMapping("/parse/{resumeId}")
        public ResponseEntity<String> parseResume(
                @PathVariable Long resumeId) {

            String resumeText = resumeService.parseResume(resumeId);

            return ResponseEntity.ok(resumeText);
        }

    }