package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {
    @Query(nativeQuery = true,value = "select * from tbl_class where teacher_id=?")
    List<Class> findClassByTeacherId(String teacherId);
}
