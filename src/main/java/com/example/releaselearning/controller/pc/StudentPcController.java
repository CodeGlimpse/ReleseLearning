package com.example.releaselearning.controller.pc;

import com.example.releaselearning.entity.Student;
import com.example.releaselearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pcStudent")
public class StudentPcController {
    @Autowired
    private StudentService service;

    //增加一名学生
    @GetMapping("/register_stu/{id}" +
            "/{password}" +
            "/{name}" +
            "/{classId}")
    public ModelAndView add(@PathVariable String id,
                            @PathVariable String password,
                            @PathVariable String name,
                            @PathVariable String classId){
        ModelAndView modelAndView = new ModelAndView();
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException();
        }
        if(StringUtils.isEmpty(password)){
            throw new RuntimeException();
        }
        if(StringUtils.isEmpty(name)){
            throw new RuntimeException();
        }
        if(StringUtils.isEmpty(classId)){
            throw new RuntimeException();
        }
        Student student = new Student();
        student.setStudentId(id);
        student.setName(name);
        student.setPassword(password);
        student.setClassId(classId);
        //增加
        String str = service.addStudent(student);
        if(str.equals("false")){
            throw new RuntimeException();
        }
        return modelAndView;
    }




}
