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
@Table(name = "tbl_exam")
public class Exam {

    @Id
    @NotEmpty(message = "考试题目不能为空")
    @Column(name = "exam_id")
    private String ExamId;

    @NotEmpty(message = "考试内容不能为空")
    @Column(name = "exam_content")
    private String examContent;

    @NotNull(message = "所属班级不能为空")
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @JoinColumn(name = "class_id")
    private Class classId;

    @NotEmpty(message = "考试状态不能为空")
    @Column(name = "status")
    private int status;
}
