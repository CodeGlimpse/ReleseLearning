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
@Table(name = "tbl_student")
public class Student {
    @Id
    @NotEmpty(message = "学号不能为空")
    @Column(name = "student_id")
    private String studentId;

    @NotEmpty(message = "姓名不能为空")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "班级不能为空")
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @JoinColumn(name = "class")
    private Class classId;

    @NotEmpty(message = "密码不能为空")
    @Column(name = "password")
    private String password;

    @Override
    public String toString() {
        return "Student{" + "studentId='" + studentId + '\'' + ", name='" + name + '\'' + ", classId='" + classId + '\'' + ", password='" + password + '\'' + '}';
    }

}
