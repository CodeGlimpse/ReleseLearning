package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>{
    Teacher findByTeacherId(String teacherId);
}
