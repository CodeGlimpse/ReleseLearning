package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {
    List<Homework> findHomeworkByClassId(Class classId);
}
