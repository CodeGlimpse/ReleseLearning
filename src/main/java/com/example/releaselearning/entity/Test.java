package com.example.releaselearning.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tbl_test")
public class Test implements Serializable {
    @Id
    private String testId;
    @NotEmpty(message = "考试开始时间为空")
    private String testBeginTime;
    @NotEmpty(message = "考试开始时间为空")
    private String testEndTime;
    @NotEmpty(message = "考试开始时间为空")
    private String classId;
    @NotEmpty(message = "考试开始时间为空")
    private String testContent;
    @NotEmpty(message = "考试开始时间为空")
    private String teacherId;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestBeginTime() {
        return testBeginTime;
    }

    public void setTestBeginTime(String testBeginTime) {
        this.testBeginTime = testBeginTime;
    }

    public String getTestEndTime() {
        return testEndTime;
    }

    public void setTestEndTime(String testEndTime) {
        this.testEndTime = testEndTime;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
