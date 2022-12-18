package com.example.releaselearning.controller;


import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.StudentRepository;
import com.example.releaselearning.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping
public class RegisterController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRepository classRepository;

    //注册页面
    @GetMapping("/register")
    public String getCreateRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String postCreateRegisterPage() {
        return "register";
    }

    //学生注册
    @PostMapping("/register/student")
    public ModelAndView registerStudent(String id, String name, String password, String secondPassword, String classId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/register");

        System.out.println(id + " ");//test

        if (password.equals(secondPassword)) {
            Student stu = new Student();
            stu.setStudentId(id);
            stu.setName(name);
            stu.setPassword(password);
            Optional<Class> class_id = classRepository.findById(classId);
            if (class_id.isPresent()) {
                stu.setClassId(class_id.get());
                studentRepository.save(stu);
                modelAndView.setViewName("redirect:/login");
                return modelAndView;
            }
        }

        return modelAndView;
    }

    //老师注册
    @PostMapping("/register/teacher")
    public ModelAndView registerTeacher(String id, String name, String password, String secondPassword) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/register");

        System.out.println(id + " ");//test

        if (password.equals(secondPassword)) {
            Teacher tec = new Teacher();
            tec.setTeacherId(id);
            tec.setName(name);
            tec.setPassword(password);
            teacherRepository.save(tec);

            modelAndView.setViewName("redirect:/login");
            return modelAndView;

        }

        return modelAndView;
    }
}
