package com.example.releaselearning.repository;

import com.example.releaselearning.entity.ClassTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRepository extends JpaRepository<ClassTbl, String>, JpaSpecificationExecutor<ClassTbl> {
}
