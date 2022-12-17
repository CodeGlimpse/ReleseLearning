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
@Table(name = "tbl_student")
public class Student {
    @Id
    @NotEmpty(message = "学号不能为空")
    @Column(name = "student_id")
    private String studentId;

    @NotEmpty(message = "姓名不能为空")
    @Column(name = "name")
    private String name;

    @NotNull(message = "班级不能为空")
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @JoinColumn(name = "class_id")
    private Class classId;

    @NotEmpty(message = "密码不能为空")
    @Column(name = "password")
    private String password;

}
