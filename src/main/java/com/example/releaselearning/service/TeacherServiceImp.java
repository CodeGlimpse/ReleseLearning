package com.example.releaselearning.service;

import com.alibaba.fastjson2.JSON;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.TeacherRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServiceImp implements TeacherService{

    @Autowired
    private TeacherRepository teacherDAO;

    @Override
    public String addTeacher(Teacher teacher) {
        Teacher save = teacherDAO.save(teacher);
        if(save!=null){
            return "1";
        }
        return "0";
    }

    @Override
    public String findAllAttribute(String id) {
        System.out.println(id);
        Optional<Teacher> optional = teacherDAO.findById(id);

        if(optional != null && optional.isPresent()) {
            Teacher teacher = optional.get();
            if(teacher!=null){
                //String json = JSONUtils.valueToString(teacher);
                String json = JSON.toJSONString(teacher);
                System.out.println(json+"666");
                return json;
            }
        }
        return "false";
    }

    @Override
    public Teacher findByTeacherId(String id) {
        Optional<Teacher> optional = teacherDAO.findById(id);
        Teacher teacher = optional.get();
        if(teacher!=null){
            return teacher;
        }
        return null;
    }
}
