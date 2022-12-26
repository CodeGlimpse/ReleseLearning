package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/stu")
public class StuIndexController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/index/{studentId}")
    public String getStudentIndex(Map<String,Object> map, @PathVariable("studentId")
            String studentId){
        return postStudentIndex(map,studentId);
    }
    @PostMapping("/index/{studentId}")
    public String postStudentIndex(Map<String,Object> map, @PathVariable("studentId")
            String studentId){
        //通过student_id得到学生的个人信息
        Optional<Student> student= studentRepository.findById(studentId);
        if (student.isPresent()){
            //将得到的所有信息输入到map
            String name=student.get().getName();
            String class_id =student.get().getClassId().getClassId();
            map.put("name",name);
            map.put("student_id",studentId);
            map.put("class", class_id);
        }
        //返回页面student.html
        return "student";
    }

    //聊天室
    @GetMapping("/chatroom/{studentId}")
    public String ToChatroom(Map<String, Object> map, @PathVariable
            String studentId) {
        map.put("number",0);
        map.put("userId",studentId);
        return "chatroom";
    }
}