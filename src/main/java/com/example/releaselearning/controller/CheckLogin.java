package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.service.StudentService;
import com.example.releaselearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/checkLogin")
public class CheckLogin {
    @Autowired
    StudentService serviceStudent;
    @Autowired
    TeacherService serviceTeacher;
    @GetMapping("/student")
    public String CheckStudent(Map<String, Object> map ,String userId , String password , String che){
        map.put("name", "Themeleaf");
        System.out.println(userId+ " " +password +" "+ che);
        if(che.equals("老师")){
            //通过student_id得到学生的个人信息
            Teacher teacher =  serviceTeacher.findByTeacherId(userId);
            System.out.println(teacher);
            if(teacher == null){
                return "login";
            }else if(teacher.getPassword().equals(password)){
                return "teacher";
            }else{
                return "login";
            }
        }else{
            //通过student_id得到学生的个人信息
            Student student= serviceStudent.findByStudentId(userId);
            System.out.println(student);
            if(student == null){
                return "login";
            }else if(student.getPassword().equals(password)){
                return "student";
            }else{
                return "login";
            }
        }
    }
}
