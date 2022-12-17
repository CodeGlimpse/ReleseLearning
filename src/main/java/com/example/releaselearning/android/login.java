package com.example.releaselearning.android;

import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/android")
public class login {

    @Autowired
    private StudentRepository studentRepository;

    @ResponseBody
    @GetMapping("/login/{studentId}/{studentPwd}")
    public String checkLogin(@PathVariable String studentId, @PathVariable String studentPwd) {

        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isPresent()) {
            System.out.println(student);//test

            if (student.get().getPassword().equals(studentPwd)) {
                return "true";
            }
        }
        return "false";
    }
}
