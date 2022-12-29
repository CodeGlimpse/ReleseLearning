package com.example.releaselearning.android;

import cn.hutool.json.JSONUtil;
import com.example.releaselearning.entity.*;
import com.example.releaselearning.repository.ExamDetailRepository;
import com.example.releaselearning.repository.ExamRepository;
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

    @Autowired
    private ExamDetailRepository examDetailRepository;

    @ResponseBody
    @GetMapping("/getExamAllByStuId/{studentId}")
    public String getHomeWorkAllByStuId(@PathVariable String studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        List<Exam> examList = examRepository.findExamByClassId(student.get().getClassId());
        String msg = JSONUtil.toJsonStr(examList);
        System.out.println(msg);
        return msg;
    }

    @ResponseBody
    @GetMapping("/getExamWorkByStudentIdAndExamId/{studentId}/{examId}")
    public String getExamWorkByStudentIdAndExamId(@PathVariable String studentId ,@PathVariable String examId){
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Exam> exam = examRepository.findById(examId);
        Optional<ExamDetail> examDetail = examDetailRepository.findExamDetailByStudentIdAndExamId(student.get() , exam.get());

        String msg = "";

        if(examDetail.isPresent()){
            msg = JSONUtil.toJsonStr(examDetail.get());
        }
        return msg;
    }
}
