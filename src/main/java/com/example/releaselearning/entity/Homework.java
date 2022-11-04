package com.example.releaselearning.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tbl_homework")
public class Homework implements Serializable {
    @Id
    private String homeworkId;
    @NotEmpty(message = "作业开始时间")
    private String homeworkBeginTime;
    @NotEmpty(message = "作业开始时间")
    private String homeworkEndTime;
    @NotEmpty(message = "作业开始时间")
    private String homeworkContent;
    @NotEmpty(message = "作业开始时间")
    private String classId;
    @NotEmpty(message = "作业开始时间")
    private String teacherId;

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkBeginTime() {
        return homeworkBeginTime;
    }

    public void setHomeworkBeginTime(String homeworkBeginTime) {
        this.homeworkBeginTime = homeworkBeginTime;
    }

    public String getHomeworkEndTime() {
        return homeworkEndTime;
    }

    public void setHomeworkEndTime(String homeworkEndTime) {
        this.homeworkEndTime = homeworkEndTime;
    }

    public String getHomeworkContent() {
        return homeworkContent;
    }

    public void setHomeworkContent(String homeworkContent) {
        this.homeworkContent = homeworkContent;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
