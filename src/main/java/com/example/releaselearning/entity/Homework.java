package com.example.releaselearning.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_homework")
public class Homework {

    @Id
    @NotEmpty(message = "作业题目不能为空")
    @Column(name = "homework_id")
    private String homeworkId;

    @NotEmpty(message = "作业开始时间不能为空")
    @Column(name = "homework_begin_time")
    private String homeworkBeginTime;

    @NotEmpty(message = "作业结束时间不能为空")
    @Column(name = "homework_end_time")
    private String homeworkEndTime;

    @NotEmpty(message = "作业内容不能为空")
    @Column(name = "homework_content")
    private String homeworkContent;

    @NotNull(message = "所属学生不能为空")
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @JoinColumn(name = "student")
    private Student student;

    @NotEmpty(message = "分数不能为空")
    @Column(name = "score")
    private int score;

    @NotEmpty(message = "作业状态不能为空")
    @Column(name = "status")
    private String status;

    @NotEmpty(message = "作业类型判断不能为空")
    @Column(name = "exam")
    private int exam;
}
