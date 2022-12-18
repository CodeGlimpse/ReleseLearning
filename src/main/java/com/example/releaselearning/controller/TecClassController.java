package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        //遍历数据库班级表，寻找techer_id符合要求的班级列表
        List<Class> classes= classRepository.findAll();
        System.out.println(classes);
        List<Class> classesYes = new ArrayList<>();
        for(Class cla : classes){
            if(cla.getTeacherId().getTeacherId().equals(teacherId)){
                classesYes.add(cla);
            }
            //传递数据
            map.put("classesYes", classesYes);
            map.put("teacherId", teacherId);
        }
        System.out.println(map.get(classesYes));

        //返回页面classList.html
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
