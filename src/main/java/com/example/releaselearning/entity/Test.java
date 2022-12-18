package com.example.releaselearning.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_test")
public class Test {
    @Id
    @Column(name = "test_id")
    @NotEmpty(message = "考试id不能为空")
    String testId;


    @Column(name = "test_begin_time")
    @NotEmpty(message = "考试起始时间不能为空")
    Date testBeginTime;

    @Column(name = "test_end_time")
    @NotEmpty(message = "考试终止时间不能为空")
    Date testEndTime;

    @Column(name = "class_id")
    @NotEmpty(message = "班级id不能为空")
    String classId;

    @Column(name = "test_content")
    @NotEmpty(message = "考试内容不能为空")
    String testContent;

    @Column(name = "teacher_id")
    @NotEmpty(message = "老师id不能为空")
    String teacherId;


}
