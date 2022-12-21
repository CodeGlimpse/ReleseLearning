package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.StudentRepository;
import com.example.releaselearning.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/tec")
public class TecClassController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/newClass/{teacherId}")
    public String getNewClassPage(Map<String, Object> map, @PathVariable String teacherId) {
        return postNewClassPage(map, teacherId);
    }

    @PostMapping("/newClass/{teacherId}")
    public String postNewClassPage(Map<String, Object> map, @PathVariable String teacherId) {
        //创建班级页面
        Optional<Teacher> user = teacherRepository.findById(teacherId);
        if (user.isPresent()) {
            map.put("teacherId", teacherId);
            map.put("teacherName", user.get().getName());
        }
        return "newClass";
    }

    @PostMapping("/createClass")
    public ModelAndView createClass(String teacherId, String classId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tec/newClass/" + teacherId);
        //创建班级返回函数
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        Optional<Class> tmp = classRepository.findById(classId);
        if (!tmp.isPresent() && teacher.isPresent()) {
            Class newClass = new Class();
            newClass.setClassId(classId);
            newClass.setTeacherId(teacher.get());
            classRepository.save(newClass);
            modelAndView.setViewName("redirect:/tec/index/" + teacherId);
        }
        return modelAndView;
    }

    @GetMapping("/classList/{teacherId}")
    public String getClassListPage(Map<String, Object> map, @PathVariable String teacherId) {
        return postClassListPage(map, teacherId);
    }

    @PostMapping("/classList/{teacherId}")
    public String postClassListPage(Map<String, Object> map, @PathVariable String teacherId) {
        //班级列表页面，查询该老师所管辖的所有班级
        List<Class> classes = classRepository.findClassByTeacherId(teacherId);
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        teacher.ifPresent(value -> map.put("teacherName", value.getName()));
        if (classes != null) {
            map.put("classes", classes);
        }
        return "classList";
    }

    @GetMapping("/classDetail/{classId}")
    public String getClassDetailPage(Map<String, Object> map, @PathVariable String classId) {
        return postClassDetailPage(map, classId);
    }

    @PostMapping("/classDetail/{classId}")
    public String postClassDetailPage(Map<String, Object> map, @PathVariable String classId) {
        //班级详情页面
        Optional<Class> cla = classRepository.findById(classId);
        List<Student> stus = null;
        if (cla.isPresent()) {
            stus = studentRepository.findStudentByClassId(cla.get());
        }

        System.out.println(stus);//test

        if (stus != null) {
            map.put("students", stus);
        }
        map.put("classId", classId);

        return "classDetail";
    }

    @GetMapping("/detele/{classId}/{studentId}")
    public String getDeteleStudent(Map<String, Object> map, @PathVariable String classId,@PathVariable String studentId) {
        return postDeteleStudent(map, classId,studentId);
    }

    @PostMapping("/detele/{classId}/{studentId}")
    public String postDeteleStudent(Map<String, Object> map,@PathVariable String classId, @PathVariable String studentId) {
        System.out.println(studentId);
        studentRepository.deleteById(studentId);
        Optional<Class> clas =  classRepository.findById(classId);
        List<Student> students = null;
        if(clas.isPresent())students = studentRepository.findStudentByClassId(clas.get());

        if(students!=null){
            map.put("students", students);
        }
        return "classDetail";
    }


}
