package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Exam;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.ExamRepository;
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

    @GetMapping("/exam/addPage/{teacher_id}/{class_id}")
    public String getAddExamPage(Map<String,Object> map, @PathVariable
    String teacher_id ,@PathVariable String class_id){
        return postAddExamPage(map,teacher_id , class_id);
    }
    @PostMapping("/exam/addPage/{teacher_id}/{class_id}")
    public String postAddExamPage(Map<String,Object> map, @PathVariable
    String teacher_id ,@PathVariable String class_id){

        return "tecAddExam";
    }

    @PostMapping("/exam/add/{teacher_id}/{class_id}")
    public String postAddExam(Map<String,Object> map,@PathVariable String teacher_id,@PathVariable String class_id , String examId , String examContent){
        System.out.println(class_id+" " +examId +" " +examContent);
        Optional<Class> clas =  classRepository.findById(class_id);
        Exam exam = null;
        if(clas.isPresent()) exam = new Exam(examId,examContent ,clas.get() , 0);
        if(exam!=null) {
            Exam save = examRepository.save(exam);
        }
        return "tecAddExam";
    }


}