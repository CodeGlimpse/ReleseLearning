package com.example.releaselearning.service;

import com.alibaba.fastjson.JSON;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService{

    @Autowired
    private StudentRepository studentDAO;

    //新增数据
    @Override
    public String addStudent(Student student) {
        Student save = studentDAO.save(student);
        if(save!=null){
            return "1";
        }
        return "0";
    }

    //所有属性值
    @Override
    public String findAllAttribute(String studentId) {
        Optional<Student> optional = studentDAO.findById(studentId);
        if (optional != null && optional.isPresent()) {
            Student student = optional.get();
            if(student!=null){
                String json = JSON.toJSONString(student);
                System.out.println(json);
                return json;
            }
        }
        return "false";
    }

    //查询登录
    @Override
    public Student findByStudentId(String studentId) {
        Optional<Student> optional = studentDAO.findById(studentId);
        if(optional != null && optional.isPresent()){
            Student student = optional.get();
            if(student!=null){
                return student;
            }
        }
        return null;
    }
}
