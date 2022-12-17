package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/tec")
public class TecIndexController {
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/index/{teacherId}")
    public String getTeacherIndex(Map<String,Object> map, @PathVariable
            String teacherId){
        return postTeacherIndex(map, teacherId);
    }
    @PostMapping("/index/{teacherId}")
    public String postTeacherIndex(Map<String,Object> map, @PathVariable
            String teacherId){
        //通过teacherId得到教师的个人信息
        Optional<Teacher> teacher= teacherRepository.findById(teacherId);
        String name= teacher.get().getName();
        //将得到的所有信息输入到map
        map.put("teacher_id", teacherId);
        map.put("name",name);
        //返回页面teacher.html
        return "teacher";
    }
}
