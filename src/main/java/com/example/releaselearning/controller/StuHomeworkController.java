package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Test;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/stu")
public class StuHomeworkController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;

    @GetMapping("/homework/{studentId}")
    public String getHomeworkPage(Map<String,Object> map, @PathVariable
            String studentId){
        return postHomeworkPage(map, studentId);
    }
    @PostMapping("/homework/{studentId}")
    public String postHomeworkPage(Map<String,Object> map, @PathVariable
            String studentId){
        //作业界面
        //考试界面
        //查询该学生所在班级
        Optional<Student> now  = studentRepository.findById(studentId);
        //查询所在班级的所有作业
        System.out.println(now.get());
        List<Homework> allHomework = homeworkRepository.findByStudent(now.get());
        System.out.println(allHomework.size());
        map.put("allHomework" , allHomework);
        map.put("student" , now.get());

        return "stuHomework";
    }
}
