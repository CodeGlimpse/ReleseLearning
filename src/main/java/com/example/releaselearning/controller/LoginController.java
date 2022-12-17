package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
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
public class LoginController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    //初始登陆界面
    @GetMapping("/login")
    public String getCreateLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String postCreateLoginPage() {
        return "login";
    }

    //登陆验证检查
    @PostMapping("/check")
    public ModelAndView checkLogin(String userId, String password, String che) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");

        System.out.println(userId + " " + password + " " + che);//test

        if (che.equals("teacher")) {
            Optional<Teacher> teacher = teacherRepository.findById(userId);
            if (teacher.isPresent()) {
                System.out.println(teacher);//test

                if (teacher.get().getPassword().equals(password)) {
                    modelAndView.setViewName("redirect:/tec/index/" + userId);
                }
            }
        } else {
            Optional<Student> student = studentRepository.findById(userId);

            if (student.isPresent()) {
                System.out.println(student);//test

                if (student.get().getPassword().equals(password)) {
                    modelAndView.setViewName("redirect:/stu/index/" + userId);
                }
            }
        }
        return modelAndView;
    }

}
