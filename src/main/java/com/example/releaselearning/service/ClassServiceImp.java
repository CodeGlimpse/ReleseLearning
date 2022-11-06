package com.example.releaselearning.service;

import com.alibaba.fastjson2.JSON;
import com.example.releaselearning.entity.ClassTbl;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImp implements ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Override
    public String findByTeacherId(String teacherId) {
        List<ClassTbl> list = classRepository.findByTeacherId(teacherId);
        if(list.size() > 0){
            String json = JSON.toJSONString(list);
            return json;
        }
        return "";
    }
}
