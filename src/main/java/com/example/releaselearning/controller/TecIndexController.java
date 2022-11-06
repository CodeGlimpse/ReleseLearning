package com.example.releaselearning.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.releaselearning.entity.ClassTbl;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.service.ClassService;
import com.example.releaselearning.service.HomeworkService;
import com.example.releaselearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tec")
public class TecIndexController {
    @Autowired //调用service类
    TeacherService teacherService;
    @Autowired
    private ClassService classService;

    @Autowired
    private HomeworkService homeworkService;
    @GetMapping("/getTeacherIndex/{teacher_id}")
    public String getStudentIndex(Map<String,Object> map,
                                  @PathVariable String teacher_id){
        //通过teacher_id得到教师的个人信息
        Teacher teacher= teacherService.findByTeacherId(teacher_id);
        String name= teacher.getName();
        //将得到的所有信息输入到map
        map.put("teacher_id",teacher_id);
        map.put("name",name);
        //返回页面teacher.html
        return "teacher";
    }

    //李冰玉写的方法
    @GetMapping("/getTeacherClassList")
    public ModelAndView getTeacherClassList(String teacherId,String teacherName){
        ModelAndView modelAndView = new ModelAndView();
        if(StringUtils.isEmpty(teacherId)){
            throw new RuntimeException();
        }
        String json = classService.findByTeacherId(teacherId);
        if(!StringUtils.isEmpty(json)){
            JSONArray jsonArray = JSON.parseArray(json);
            List<ClassTbl> classTblList = jsonArray.toJavaList(ClassTbl.class);
            modelAndView.addObject("classList",classTblList);
        }
        modelAndView.addObject("teacherName",teacherName);
        modelAndView.setViewName("/teacher_class");
        return modelAndView;
    }

    //李冰玉写的方法
    @GetMapping("/getTeacherHomeworkList")
    public ModelAndView getTeacherHomeworkList(String teacherId,String teacherName){
        ModelAndView modelAndView = new ModelAndView();
        if(StringUtils.isEmpty(teacherId)){
            throw new RuntimeException();
        }
        String json = homeworkService.findByTeacherId(teacherId);
        if(!StringUtils.isEmpty(json)){
            JSONArray jsonArray = JSON.parseArray(json);
            List<Homework> homeworkList = jsonArray.toJavaList(Homework.class);
            modelAndView.addObject("homeworkList",homeworkList);
        }
        /*List<Homework> homeworkList = new ArrayList<>();
        for(int i = 0;i<3;i++){
            Homework homework = new Homework();
            homework.setHomeworkId(i+"");
            homework.setTeacherId(i+"");
            homework.setClassId(i+"");
            homework.setHomeworkBeginTime(new Date());
            homework.setHomeworkEndTime(new Date());
            homework.setHomeworkContent(i+"");
            homeworkList.add(homework);
        }*/
        modelAndView.addObject("teacherName",teacherName);

        modelAndView.setViewName("/teacher_homework");
        return modelAndView;

    }
}
