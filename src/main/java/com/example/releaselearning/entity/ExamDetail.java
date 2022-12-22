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
@Table(name = "tbl_exam_detail")
public class ExamDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @NotNull(message = "考试外键不能为空")
    @JoinColumn(name = "exam_id")
    private Exam examId;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @NotNull(message = "所属学生不能为空")
    @JoinColumn(name = "student_id")
    private Student studentId;

    @NotEmpty(message = "答卷位置不能为空")
    @Column(name = "exam_file")
    private String examFile;

    @NotNull(message = "分数不能为空")
    @Column(name = "exam_score")
    private int examScore;
}
