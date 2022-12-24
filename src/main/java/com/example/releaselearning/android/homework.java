package com.example.releaselearning.android;

import cn.hutool.json.JSONUtil;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/android/homework")
public class homework {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HomeworkRepository homeworkRepository;

    @ResponseBody
    @GetMapping("/getHomeWorkAllByStuId/{studentId}")
    public String getHomeWorkAllByStuId(@PathVariable String studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        List<Homework> homeworkList = homeworkRepository.findHomeworkByClassId(student.get().getClassId());
        String msg = JSONUtil.toJsonStr(homeworkList);
        return msg;
    }



}
