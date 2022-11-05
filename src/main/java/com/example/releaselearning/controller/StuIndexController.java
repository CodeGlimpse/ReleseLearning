package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Student;
import com.example.releaselearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/stu")
public class StuIndexController {
    @Autowired
    StudentService service;

    @GetMapping("/getStudentIndex/{studentId}")
    public String getStudentIndex(Map<String,Object> map, @PathVariable("studentId")
            String studentId){
        //通过student_id得到学生的个人信息
        Student student= service.findByStudentId(studentId);
        //将得到的所有信息输入到map
        String name=student.getName();
        String classid=student.getClassId();
        map.put("name",name);
        map.put("student_id",studentId);
        map.put("class", classid);
        //返回页面student.html
        return "student";
    }
}
