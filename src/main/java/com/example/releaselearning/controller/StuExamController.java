package com.example.releaselearning.controller;

import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/stu")
public class StuExamController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;

    @GetMapping("/exam/{studentId}")
    public String getExamPage(Map<String,Object> map, @PathVariable
            String studentId){
        return postHomeworkPage(map, studentId);
    }
    @PostMapping("/exam/{studentId}")
    public String postHomeworkPage(Map<String,Object> map, @PathVariable
            String studentId){
        //考试界面

        return "stuExam";
    }
}
