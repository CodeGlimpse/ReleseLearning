package com.example.releaselearning.repository;

import com.example.releaselearning.entity.ExamDetail;
import com.example.releaselearning.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamDetailRepository extends JpaRepository<ExamDetail, String> {
    List<ExamDetail> findExamDetailByStudentId(Student studentId);
}