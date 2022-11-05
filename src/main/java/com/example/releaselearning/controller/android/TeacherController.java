package com.example.releaselearning.controller.android;

import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    //增加操作
    @GetMapping("/register_tea/{id}/{password}/{name}")
    public String add(@PathVariable String id,
                           @PathVariable String password,
                           @PathVariable String name){
        if(StringUtils.isEmpty(id)){
            return "0";
        }
        if(StringUtils.isEmpty(password)){
            return "0";
        }
        if(StringUtils.isEmpty(name)){
            return "0";
        }
        Teacher teacher = new Teacher();
        teacher.setTeacher_id(id);
        teacher.setName(name);
        teacher.setPassword(password);

        //增加
        String str = service.addTeacher(teacher);
        if(str.equals("false")){
            return "0";
        }
        return "1";
    }

    //查询所有数据
    //@GetMapping("/teacher?tea_id={id}")
    @GetMapping("/teacher/{id}")
    public String findAllAttribute(@PathVariable String id){
        if(StringUtils.isEmpty(id)){
            return "false";
        }
        String str = service.findAllAttribute(id);
        if(str.equals("false")){
            return "false";
        }
        return str;
    }

    //登录操作
    @GetMapping("/login_tea/{id}/{password}")
    public String findById(@PathVariable String id,
                        @PathVariable String password){
        if(StringUtils.isEmpty(id)){
            return "false";
        }
        if(StringUtils.isEmpty(password)){
            return "false";
        }
        Teacher tea = service.findByTeacherId(id);
        if(tea == null){
            return "false";
        }
        if(!password.equals(tea.getPassword())){
            return "false";
        }
        return "true";
    }
}
