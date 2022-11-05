package com.example.releaselearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping
public class ToRegisterController {

    @GetMapping("/register_stu")
    @PostMapping("/register_stu")
    public String registerStudent(Map<String, Object> map) {
        map.put("name", "Themeleaf");

        return "register_stu";
    }

    @GetMapping("/register_tea")
    @PostMapping("/register_tea")
    public String registerTeacher(Map<String, Object> map) {
        map.put("name", "Themeleaf");

        return "register_tea";
    }
}
