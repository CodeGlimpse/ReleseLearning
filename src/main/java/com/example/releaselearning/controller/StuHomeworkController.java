package com.example.releaselearning.controller;

import com.example.releaselearning.entity.HwDetail;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.HwDetailRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/stu")
public class StuHomeworkController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private HwDetailRepository hwDetailRepository;

    @GetMapping("/homework/{studentId}")
    public String getHomeworkPage(Map<String, Object> map, @PathVariable
            String studentId) {
        return postHomeworkPage(map, studentId);
    }

    @PostMapping("/homework/{studentId}")
    public String postHomeworkPage(Map<String, Object> map, @PathVariable
            String studentId) {
        //作业界面
        //查询该学生所在班级
        Optional<Student> now = studentRepository.findById(studentId);
        //查询所有作业
        if(now.isPresent()) {
            Student student = now.get();
            map.put("student", student);
            List<HwDetail> allHomework = hwDetailRepository.findHwDetailByStudentId(student);
            System.out.println(allHomework.size());
            map.put("allHomework", allHomework);
        }
        return "stuHomework";
    }
}
