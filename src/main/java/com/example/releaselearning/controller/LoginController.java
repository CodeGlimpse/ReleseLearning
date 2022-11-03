package com.example.releaselearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    @PostMapping("/login")
    public String getCreateLoginPage(Map<String, Object> map) {
        map.put("name", "Themeleaf");

        return "login";
    }
}
