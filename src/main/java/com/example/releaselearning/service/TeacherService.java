package com.example.releaselearning.service;

import com.example.releaselearning.entity.Teacher;

public interface TeacherService {

    String addTeacher(Teacher teacher);

    String findAllAttribute(String id);

    Teacher findByTeacherId(String id);
}
