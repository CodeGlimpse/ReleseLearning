package com.example.releaselearning.controller;

import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/tec")
public class TecExamController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;

    @GetMapping("/exam/{classId}")
    public String getExamPage(Map<String,Object> map, @PathVariable
            String classId){
        return postExamPage(map,classId);
    }
    @PostMapping("/exam/{classId}")
    public String postExamPage(Map<String,Object> map, @PathVariable
            String classId){
        //考试界面

        return "tecExam";
    }
}