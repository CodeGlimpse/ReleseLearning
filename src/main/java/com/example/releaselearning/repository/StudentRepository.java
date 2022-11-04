package com.example.releaselearning.repository;

import com.example.releaselearning.entity.ClassTbl;
import com.example.releaselearning.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
}
