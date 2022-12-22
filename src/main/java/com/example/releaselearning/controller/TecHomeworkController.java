package com.example.releaselearning.controller;

import com.example.releaselearning.entity.*;
import com.example.releaselearning.entity.Class;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.HwDetailRepository;
import com.example.releaselearning.repository.StudentRepository;
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
public class TecHomeworkController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private HwDetailRepository hwDetailRepository;

    @GetMapping("/homework/{classId}")
    public String getHomeworkPage(Map<String, Object> map, @PathVariable String classId) {
        return postHomeworkPage(map, classId);
    }

    @PostMapping("/homework/{classId}")
    public String postHomeworkPage(Map<String, Object> map, @PathVariable String classId) {
        //作业界面
        Class cla;
        Optional<Class> tmp = classRepository.findById(classId);
        List<Homework> homeworkList = null;
        if (tmp.isPresent()) {
            cla = tmp.get();
            homeworkList = homeworkRepository.findHomeworkByClassId(cla);
            map.put("cla", cla);
        }
        map.put("homeworkList", homeworkList);
        return "tecHomework";
    }

    @GetMapping("/homework/addPage/{teacher_id}/{class_id}")
    public String getAddHomeworkPage(Map<String, Object> map, @PathVariable String teacher_id, @PathVariable String class_id) {
        return postAddHomeworkPage(map, teacher_id, class_id);
    }

    @PostMapping("/homework/addPage/{teacher_id}/{class_id}")
    public String postAddHomeworkPage(Map<String, Object> map, @PathVariable String teacher_id, @PathVariable String class_id) {
        return "tecAddHomework";
    }

    @PostMapping("/homework/add/{teacher_id}/{class_id}")
    public ModelAndView postAddHomework(@PathVariable String teacher_id, @PathVariable String class_id, String homeworkId, String homeworkContent) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tec/homework/addPage/" + teacher_id + "/" + class_id);
        System.out.println(class_id + " " + homeworkId + " " + homeworkContent);
        Optional<Class> clas = classRepository.findById(class_id);
        if (clas.isPresent()) {
            Homework homework = new Homework(homeworkId, homeworkContent, clas.get());
            if (homework != null) {
                homeworkRepository.save(homework);
                List<Student> students = studentRepository.findStudentByClassId(clas.get());
                for (Student student : students) {
                    HwDetail hwDetail = new HwDetail(0,homework, student, "#", -1, "未完成");
                    hwDetailRepository.save(hwDetail);
                }
                modelAndView.setViewName("redirect:/tec/homework/" + teacher_id);
            }
        }
        return modelAndView;
    }

}
