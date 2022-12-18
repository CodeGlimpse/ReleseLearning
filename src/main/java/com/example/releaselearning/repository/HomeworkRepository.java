package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {
    List<Homework> findHomeworkByStudentAndExam(Student student,int exam);
    List<Homework> findByStudent(Student student);
}
