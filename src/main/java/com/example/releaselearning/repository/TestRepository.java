package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TestRepository extends JpaRepository<Test, String> {
    List<Test> findByClassId(String class_id);
}
