package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student>  findStudentByClassId(Class cla);
}
