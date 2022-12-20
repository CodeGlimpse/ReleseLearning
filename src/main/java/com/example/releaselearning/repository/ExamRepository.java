package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, String> {
    List<Exam> findExamByClassId(Class cla);
}