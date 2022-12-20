package com.example.releaselearning.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_hw_detail")
public class HwDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @NotEmpty(message = "作业题目不能为空")
    @JoinColumn(name = "homework_id")
    private Homework homeworkId;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @NotEmpty(message = "所属学生不能为空")
    @JoinColumn(name = "student_id")
    private Student studentId;

    @NotEmpty(message = "作业位置不能为空")
    @Column(name = "homework_file")
    private String homeworkFile;

    @NotEmpty(message = "分数不能为空")
    @Column(name = "homework_score")
    private int homeworkScore;
}
