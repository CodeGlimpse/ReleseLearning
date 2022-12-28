package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/webrtc")
public class Webrtc {
    @GetMapping
    public String getWebrtc(Map<String,Object> map){
        return postWebrtc(map);
    }
    @PostMapping
    public String postWebrtc(Map<String,Object> map){
        return "index";
    }
}
