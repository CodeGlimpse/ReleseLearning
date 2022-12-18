package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {
    Class findByClassId(String classId);
}
