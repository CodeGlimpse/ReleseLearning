package com.example.releaselearning.service;

import com.alibaba.fastjson2.JSON;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeworkServiceImp implements HomeworkService{
    @Autowired
    private HomeworkRepository homeworkRepository;

    @Override
    public String findByClassId(String classId) {
//        List<Homework> list = new ArrayList<>();
        List<Homework> list = homeworkRepository.findByClassId(classId);
        if(list.size() > 0){
            String json = JSON.toJSONString(list);
            return json;
        }
        return "";
    }

    @Override
    public String findByTeacherId(String teacherId) {
        List<Homework> list = homeworkRepository.findByTeacherId(teacherId);
        if(list.size() > 0){
            String json = JSON.toJSONString(list);
            return json;
        }
        return "";
    }
}
