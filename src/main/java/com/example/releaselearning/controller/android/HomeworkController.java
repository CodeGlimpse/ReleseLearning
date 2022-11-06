package com.example.releaselearning.controller.android;

import com.example.releaselearning.service.HomeworkService;
import com.example.releaselearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/homweork")
public class HomeworkController {
    @Autowired
    private HomeworkService service;

    @GetMapping("/findByClassID/{classId}")
    public String findByClassID(@PathVariable String classId){
        String str = "";
        if(StringUtils.isEmpty(classId)){
            return str;
        }
        str = service.findByClassId(classId);
        return str;
    }

    @GetMapping("/findByTeacherID/{teacherId}")
    public String findByTeacherID(@PathVariable String teacherId){
        String str = "";
        if(StringUtils.isEmpty(teacherId)){
            return str;
        }
        str = service.findByTeacherId(teacherId);
        return str;
    }
}
