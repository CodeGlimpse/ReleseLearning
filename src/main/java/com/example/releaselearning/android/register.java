package com.example.releaselearning.android;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.ClassRepository;
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
public class register {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;

    @ResponseBody
    @GetMapping("/register/{studentId}/{studentPwd}/{studentName}/{classId}")
    public String newUser(@PathVariable String studentId, @PathVariable String studentPwd, @PathVariable String classId, @PathVariable String studentName) {

        Optional<Student> temp = studentRepository.findById(studentId);
        Optional<Class> tmp = classRepository.findById(classId);
        if (!temp.isPresent() && tmp.isPresent()) {
            Student user = new Student(studentId, studentName, tmp.get(), studentPwd);
            studentRepository.save(user);
            return "true";
        }

        return "false";
    }
}
