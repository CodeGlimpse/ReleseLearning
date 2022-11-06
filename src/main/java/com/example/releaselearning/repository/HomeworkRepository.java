package com.example.releaselearning.repository;


import com.example.releaselearning.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework , String>, JpaSpecificationExecutor<Homework> {
    List<Homework> findByClassId(String classID);

    List<Homework> findByTeacherId(String teacherId);
}