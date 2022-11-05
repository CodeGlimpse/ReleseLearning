package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/tec")
public class TecIndexController {
    @Autowired //调用service类
    TeacherService teacherService;
    @GetMapping("/getTeacherIndex/{teacher_id}")
    public String getStudentIndex(Map<String,Object> map, @PathVariable
            String teacher_id){
        //通过teacher_id得到教师的个人信息
        Teacher teacher= teacherService.findByTeacherId(teacher_id);
        String name= teacher.getName();
        //将得到的所有信息输入到map
        map.put("teacher_id",teacher_id);
        map.put("name",name);
        //返回页面teacher.html
        return "teacher";
    }

}
