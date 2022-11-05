/*
package com.example.releaselearning.controller.android;

import com.example.releaselearning.entity.ClassTbl;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService service;

    //增加
    @GetMapping
    public String addClass(@PathVariable String id,
                           @PathVariable String teacherId){
        if(StringUtils.isEmpty(id)){
            return "0";
        }
        if(StringUtils.isEmpty(teacherId)){
            return "0";
        }
        ClassTbl classTbl = new ClassTbl();
        classTbl.setClassId(id);
        classTbl.setTeacherId(teacherId);
        //service增加
        String str = service.addClass(classTbl);
        if(str.equals("false")){
            return "0";
        }
        return "1";

    }
    //删除
    @GetMapping
    public String deleteClass(String id){
        if(StringUtils.isEmpty(id)){
            return "false";
        }
    }
    //修改


    //查询
    //查询所有数据
    @GetMapping("/student?stu_id={studentId}")
    //@GetMapping("/student/{studentId}")
    public String findAllAttribute(@PathVariable String studentId){
        System.out.println("666");
        if(StringUtils.isEmpty(studentId)){
            return "false";
        }
        String str = service.findAllAttribute(studentId);
        if(str.equals("false")){
            return "false";
        }
        return str;
    }

    //登录操作
    @GetMapping("/login_stu?" +
            "stu_id={studentId}&" +
            "stu_password={studentPassword}")
    public String login(@PathVariable String studentId,
                        @PathVariable String studentPassword){
        if(StringUtils.isEmpty(studentId)){
            return "false";
        }
        if(StringUtils.isEmpty(studentPassword)){
            return "false";
        }
        Student stu = service.findByStudentId(studentId);
        if(stu==null){
            return "false";
        }
        if(!studentPassword.equals(stu.getPassword())){
            return "false";
        }
        return "true";
    }

}
*/
