package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Exam;
import com.example.releaselearning.entity.ExamDetail;
import com.example.releaselearning.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
public interface ExamDetailRepository extends JpaRepository<ExamDetail, String> {
    List<ExamDetail> findExamDetailByStudentId(Student studentId);

    List<ExamDetail> findExamDetailByExamId(Exam exam);

    @Query(nativeQuery = true,value = "select * from tbl_exam_detail where id=?")
    Optional<ExamDetail> findExamDetailById(int id);

    @Modifying
    @Query(nativeQuery = true,value = "update tbl_exam_detail set exam_score=? where id=?")
    Integer updateExamDetailsById(@Param("exam_score") int examScore, @Param("id") int id);
}