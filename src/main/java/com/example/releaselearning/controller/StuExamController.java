package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Test;
import com.example.releaselearning.repository.StudentRepository;
import com.example.releaselearning.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/stu")
public class StuExamController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRepository testRepository;
    @GetMapping("/exam/{studentId}")
    public String getExamPage(Map<String,Object> map, @PathVariable
            String studentId){
        return postHomeworkPage(map, studentId);
    }
    @PostMapping("/exam/{studentId}")
    public String postHomeworkPage(Map<String,Object> map, @PathVariable
            String studentId){
        //考试界面
        //查询该学生所在班级
       Optional<Student> now  = studentRepository.findById(studentId);
        //查询所在班级的所有考试
        List<Test>allTest = testRepository.findByClassId(now.get().getClassId().getClassId());
        map.put("allTest" , allTest);
        map.put("student" , now.get());
        return "stu_test";
    }
}
