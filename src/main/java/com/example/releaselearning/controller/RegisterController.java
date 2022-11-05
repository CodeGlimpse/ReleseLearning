package com.example.releaselearning.controller;


import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.service.StudentService;
import com.example.releaselearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/registPC")
public class RegisterController {

    @Autowired
    StudentService serviceStudent;
    @Autowired
    TeacherService serviceTeacher;

    @GetMapping("/student")
    public String CheckStudent(Map<String, Object> map , String studentId ,String studentName,  String studentpassword , String studentsecondPassword, String class_id ){
        map.put("name", "Themeleaf");
        System.out.println(studentId+ " ");
        if(!studentpassword.equals(studentsecondPassword)){
            return "register_stu";
        }
            Student stu = new Student();
        stu.setStudentId(studentId);
        stu.setName(studentName);
        stu.setPassword(studentpassword);
        stu.setClassId(class_id);
            //通过student_id得到学生的个人信息
            String str =  serviceStudent.addStudent(stu);
            if(str.equals("1")){
                return "login";
            }else{
                return "register_stu";
            }
    }

    @GetMapping("/teacher")
    public String CheckStudent(Map<String, Object> map , String teacherId ,String teacherName,  String teacherpassword, String teachersecoundPassword) {
        map.put("name", "Themeleaf");
        System.out.println(teacherId + " ");
        if (!teacherpassword.equals(teachersecoundPassword)) {
            return "register_tea";
        }
        Teacher tea = new Teacher();
        tea.setTeacher_id(teacherId);
        tea.setName(teacherName);
        tea.setPassword(teacherpassword);
        String str = serviceTeacher.addTeacher(tea);
        if (str.equals("1")) {
            return "login";
        } else {
            return "register_tea";
        }
    }
}
