package com.example.releaselearning.controller.android;

import cn.hutool.http.HttpResponse;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.service.StudentService;
import com.example.releaselearning.service.StudentServiceImp;
import net.bytebuddy.asm.Advice;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

@Controller
@ResponseBody
@RequestMapping("/androidStudent")
public class StudentController {

    @Autowired
    private StudentService service;

    //增加操作
    @GetMapping("/register_stu/{studentId}" +
            "/{studentPassword}" +
            "/{studentName}" +
            "/{classId}")
    public String add(@PathVariable String studentId,
                           @PathVariable String studentPassword,
                           @PathVariable String studentName,
                           @PathVariable String classId){
        if(StringUtils.isEmpty(studentId)){
            return "0";
        }
        if(StringUtils.isEmpty(studentPassword)){
            return "0";
        }
        if(StringUtils.isEmpty(studentName)){
            return "0";
        }
        if(StringUtils.isEmpty(classId)){
            return "0";
        }
        Student student = new Student();
        student.setStudentId(studentId);
        student.setName(studentName);
        student.setPassword(studentPassword);
        student.setClassId(classId);

        //增加
        String str = service.addStudent(student);
        if(str.equals("false")){
            return "0";
        }

        return "1";

    }

    //查询所有数据
    @GetMapping("/student/{studentId}")
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
    @GetMapping("/login_stu" +
            "/{studentId}" +
            "/{studentPassword}")
    public String findById(@PathVariable String studentId,
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

    //删除数据

    //修改数据
}
