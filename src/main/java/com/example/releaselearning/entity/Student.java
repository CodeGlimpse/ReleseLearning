package com.example.releaselearning.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tbl_student")
public class Student implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Id
    private String studentId;
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @NotEmpty(message = "姓名不能为空")
    private String classId;
    @NotEmpty(message = "姓名不能为空")
    private String password;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", classId='" + classId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
