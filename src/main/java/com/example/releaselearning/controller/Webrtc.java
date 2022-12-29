package com.example.releaselearning.controller;

import com.example.releaselearning.entity.ExamDetail;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.ExamDetailRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/webrtc")
public class Webrtc {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamDetailRepository examDetailRepository;
    @GetMapping
    public String getWebrtc(Map<String,Object> map){
        return postWebrtc(map);
    }
    @PostMapping
    public String postWebrtc(Map<String,Object> map){
        return "index";
    }

    @GetMapping("/exam/detail/{examId}/{studentId}")
    public String stuExamDetail(Map<String,Object> map,@PathVariable int examId,@PathVariable String studentId){
        return postStuExamDetail(map,examId,studentId);
    }
    @PostMapping("/exam/detail/{examId}/{studentId}")
    public String postStuExamDetail(Map<String,Object> map,@PathVariable int examId,@PathVariable String studentId){
        Optional<ExamDetail> tmp = examDetailRepository.findExamDetailById(examId);
        ExamDetail examDetail = tmp.get();
        if(examDetail.getExamId().getStatus().equals("正在考试")){
            Optional<Student> now  = studentRepository.findById(studentId);
            Student student = now.get();
            map.put("examDetail",examDetail);
            map.put("student",student);
        }
        return "stuIndex";
    }

}
