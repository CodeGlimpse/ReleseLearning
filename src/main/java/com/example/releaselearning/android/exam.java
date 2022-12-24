package com.example.releaselearning.android;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.example.releaselearning.entity.Exam;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.ExamRepository;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/android/exam")
public class exam {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamRepository examRepository;

    @ResponseBody
    @GetMapping("/getExamAllByStuId/{studentId}")
    public String getHomeWorkAllByStuId(@PathVariable String studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        List<Exam> examList = examRepository.findExamByClassId(student.get().getClassId());
        String msg = JSONUtil.toJsonStr(examList);
        return msg;
    }
}
