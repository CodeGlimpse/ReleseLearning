package com.example.releaselearning.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_teacher")
public class Teacher {

    @Id
    @Column(name = "teacher_id")
    private String teacherId;

    @NotEmpty(message = "姓名不能为空")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "密码不能为空")
    @Column(name = "password")
    private String password;

}
