package com.example.releaselearning.controller;

import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/tec")
public class TecClassController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRepository classRepository;

    @GetMapping("/newClass/{teacherId}")
    public String getNewClassPage(Map<String,Object> map, @PathVariable
            String teacherId){
        return postNewClassPage(map, teacherId);
    }
    @PostMapping("/newClass/{teacherId}")
    public String postNewClassPage(Map<String,Object> map, @PathVariable
            String teacherId){
        //创建班级页面

        return "newClass";
    }

    @PostMapping("/createClass")
    public ModelAndView createClass() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/register");
        //创建班级返回函数

        return modelAndView;
    }

    @GetMapping("/classList/{teacherId}")
    public String getClassListPage(Map<String,Object> map, @PathVariable
            String teacherId){
        return postClassListPage(map, teacherId);
    }
    @PostMapping("/classList/{teacherId}")
    public String postClassListPage(Map<String,Object> map, @PathVariable
            String teacherId){
        //班级列表页面

        return "classList";
    }

    @GetMapping("/classDetail/{classId}")
    public String getClassDetailPage(Map<String,Object> map, @PathVariable
            String classId){
        return postClassDetailPage(map,classId);
    }
    @PostMapping("/classDetail/{classId}")
    public String postClassDetailPage(Map<String,Object> map, @PathVariable
            String classId){
        //班级详情页面

        return "classDetail";
    }
}
