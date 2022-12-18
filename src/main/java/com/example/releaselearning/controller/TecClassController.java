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

    @GetMapping("/newClass/{teacherId}/{name}")
    public String getNewClassPage(Map<String,Object> map, @PathVariable
            String teacherId,@PathVariable String name){
        return postNewClassPage(map, teacherId, name);
    }
    @PostMapping("/newClass/{teacherId}/{name}")
    public String postNewClassPage(Map<String,Object> map, @PathVariable
            String teacherId,@PathVariable String name){
        //创建班级页面
        map.put("teacher_id", teacherId);
        map.put("name",name);
        return "newClass";
    }

    @PostMapping("/createClass/{teacherId}")
    public ModelAndView createClass(@PathVariable String teacherId,String classId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tec/index/" + teacherId);
        //创建班级返回函数
        Optional<Teacher> teacher= teacherRepository.findById(teacherId);
        Class newClass = new Class();
        newClass.setClassId(classId);
        newClass.setTeacherId(teacher.get());
        classRepository.save(newClass);
        return modelAndView;
    }

    @GetMapping("/classList/{teacherId}")
    public String getClassListPage(Map<String,Object> map, @PathVariable
            String teacherId){
        return postClassListPage(map, teacherId);
    }
    @PostMapping("/classList/{teacherId}")
    public String postClassListPage(Map<String,Object> map, @PathVariable
            String teacherId){
        //班级列表页面，查询该老师所管辖的所有班级
        List<Class> classes = classRepository.findClassByTeacherId(teacherId);
        Optional<Teacher> teacher= teacherRepository.findById(teacherId);
        map.put("teacherName",teacher.get().getName());
        if(classes!=null){
            map.put("classList",classes);
        }
        return "classList";
    }

    @GetMapping("/classDetail/{classId}")
    public String getClassDetailPage(Map<String,Object> map, @PathVariable
            String classId){
        System.out.println("1111111111111111111111111111111111111111");
        return postClassDetailPage(map, classId);
    }
    @PostMapping("/classDetail/{classId}")
    public String postClassDetailPage(Map<String,Object> map, @PathVariable
            String classId) {
        System.out.println("22222222222222222222222222222222222");
        //班级详情页面
        Optional<Class> cla = classRepository.findById(classId);
        List<Student> stus = null;
        if (cla.isPresent()) {
            stus = studentRepository.findStudentByClassId(cla.get());
        }
        System.out.println(stus);
        if (stus != null) {
            map.put("stuList", stus);
        }
        map.put("classId", classId);
        System.out.println(map.get("stuList"));
        System.out.println(map.get("classId"));
        return "classDetail";
    }
}
