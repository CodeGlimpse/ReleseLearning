package com.example.releaselearning.service;

import com.example.releaselearning.entity.Student;

public interface StudentService {
    String addStudent(Student student);
    String findAllAttribute(String studentId);

    Student findByStudentId(String studentId);
}
