package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Exam;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.ExamRepository;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.TeacherRepository;
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
@RequestMapping("/tec")
public class TecExamController {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ExamRepository examRepository;

    @GetMapping("/exam/{classId}")
    public String getExamPage(Map<String,Object> map, @PathVariable
            String classId){
        return postExamPage(map,classId);
    }
    @PostMapping("/exam/{classId}")
    public String postExamPage(Map<String,Object> map, @PathVariable
            String classId){
        //考试界面
        Class cla;
        Optional<Class> tmp = classRepository.findById(classId);
        List<Exam> examList = null;
        if (tmp.isPresent()) {
            cla = tmp.get();
            examList = examRepository.findExamByClassId(cla);
            map.put("cla", cla);
        }
        map.put("examList", examList);
        return "tecExam";
    }
}