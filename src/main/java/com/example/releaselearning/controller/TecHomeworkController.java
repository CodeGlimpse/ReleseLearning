package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.StudentRepository;
import com.example.releaselearning.repository.TeacherRepository;
import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/tec")
public class TecHomeworkController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private StudentRepository studentRepository;

    //作业列表界面
    @GetMapping("/homework/{classId}/{exam}")
    public String getHomeworkPage(Map<String,Object> map, @PathVariable
            String classId,@PathVariable int exam){
        //通过classid获得老师id
        Teacher teacher=classRepository.findByClassId(classId).getTeacherId();
        //通过classid获取学生
        Class clas=new Class();
        clas.setClassId(classId);
        List<Student> StudentList=studentRepository.findStudentsByClassId(clas);
        //通过学号获取作业数据
        List<Homework> homeworkList=new ArrayList<>();
        for(Student student:StudentList){
            List<Homework> homeworks=homeworkRepository.findHomeworkByStudentAndExam(student,exam);
            homeworkList.addAll(homeworks);
        }
        map.put("homeworkList",homeworkList);
        map.put("exam",exam);
        map.put("teacherName",teacherRepository.findByTeacherId(teacher.getTeacherId()).getName());
        return "teacher_homework";
    }
    @PostMapping("/homework/{classId}")
    public String postHomeworkPage(Map<String,Object> map, @PathVariable
            String classId){
        //作业界面
        return "tecHomework";
    }
}
