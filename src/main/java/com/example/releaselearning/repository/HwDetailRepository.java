package com.example.releaselearning.repository;

import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.HwDetail;
import com.example.releaselearning.entity.Student;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
public interface HwDetailRepository extends JpaRepository<HwDetail, String> {
    List<HwDetail> findHwDetailByStudentId(Student studentId);

    List<HwDetail> findHwDetailsByHomeworkId(Homework homeworkId);

    @Query(nativeQuery = true,value = "select * from tbl_hw_detail where id=?")
    Optional<HwDetail> findHwDetailById(int id);

    @Modifying
    @Query(nativeQuery = true,value = "update tbl_hw_detail set homework_score=? where id=?")
    Integer updateHwDetailsById(@Param("homework_score") int homeworkScore,@Param("id") int id);


    Optional<HwDetail> findHwDetailByStudentIdAndHomeworkId(Student studentId, Homework homeworkId);
}