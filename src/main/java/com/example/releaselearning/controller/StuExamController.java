package com.example.releaselearning.controller;

import com.example.releaselearning.entity.ExamDetail;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.ExamDetailRepository;
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
public class StuExamController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamDetailRepository examDetailRepository;
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

        //查询所有考试
        if(now.isPresent()){
            Student student = now.get();
            map.put("student", student);
            List<ExamDetail> allExam = examDetailRepository.findExamDetailByStudentId(student);
            map.put("allExam" , allExam);
        }

        return "stuExam";
    }
}
