package com.example.releaselearning.repository;

import com.example.releaselearning.entity.HwDetail;
import com.example.releaselearning.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HwDetailRepository extends JpaRepository<HwDetail, String> {
    List<HwDetail> findHwDetailByStudentId(Student studentId);
}