package com.ajay.resume_analyzer.repository;

import com.ajay.resume_analyzer.entity.Resume;
import com.ajay.resume_analyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUser(User user);
}
