package com.example.releaselearning.controller;

import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Exam;
import com.example.releaselearning.entity.ExamDetail;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.ExamDetailRepository;
import com.example.releaselearning.repository.ExamRepository;
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
public class TecExamController {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamDetailRepository examDetailRepository;

    @GetMapping("/exam/{classId}")
    public String getExamPage(Map<String, Object> map, @PathVariable
            String classId) {
        return postExamPage(map, classId);
    }

    @PostMapping("/exam/{classId}")
    public String postExamPage(Map<String, Object> map, @PathVariable
            String classId) {
        //考试界面
        Class cla;
        Optional<Class> tmp = classRepository.findById(classId);
        List<Exam> examList = null;
        if (tmp.isPresent()) {
            cla = tmp.get();
            examList = examRepository.findExamByClassId(cla);
            map.put("cla", cla);
        }
        map.put("examList", examList);
        return "tecExam";
    }

    @GetMapping("/exam/addPage/{teacher_id}/{class_id}")
    public String getAddExamPage(Map<String, Object> map, @PathVariable
            String teacher_id, @PathVariable String class_id) {
        return postAddExamPage(map, teacher_id, class_id);
    }

    @PostMapping("/exam/addPage/{teacher_id}/{class_id}")
    public String postAddExamPage(Map<String,Object> map, @PathVariable
    String teacher_id ,@PathVariable String class_id){

        return "tecAddExam";
    }

    @PostMapping("/exam/add/{teacher_id}/{class_id}")
    public ModelAndView postAddExam(@PathVariable String teacher_id, @PathVariable String class_id, String examId, String examContent) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tec/exam/addPage/" + teacher_id + "/" + class_id);
        System.out.println(class_id + " " + examId + " " + examContent);
        Optional<Class> clas = classRepository.findById(class_id);
        if (clas.isPresent()) {
            Exam exam = new Exam(examId, examContent, clas.get(), "未开始");
            if (exam != null) {
                examRepository.save(exam);
                List<Student> students = studentRepository.findStudentByClassId(clas.get());
                for (Student student : students) {
                    ExamDetail examDetail = new ExamDetail(0, exam, student, "#", -1);
                    examDetailRepository.save(examDetail);
                }
                modelAndView.setViewName("redirect:/tec/exam/" + teacher_id);
            }
        }
        return modelAndView;
    }

    @GetMapping("exam/begin/{exam_id}")
    public ModelAndView getExamBegin(@PathVariable String exam_id) {
        return postExamBegin(exam_id);
    }
    @PostMapping("exam/begin/{exam_id}")
    public ModelAndView postExamBegin(@PathVariable String exam_id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Exam> tmp = examRepository.findById(exam_id);
        if (tmp.isPresent()){
            Exam exam = tmp.get();
            exam.setStatus("正在考试");
            System.out.println(1);
            examRepository.save(exam);
            modelAndView.setViewName("redirect:/tec/exam/" + exam.getClassId().getClassId());
        }
        return modelAndView;
    }

    @GetMapping("exam/end/{exam_id}")
    public ModelAndView getExamEnd(@PathVariable String exam_id) {
        return postExamEnd(exam_id);
    }
    @PostMapping("exam/end/{exam_id}")
    public ModelAndView postExamEnd(@PathVariable String exam_id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Exam> tmp = examRepository.findById(exam_id);
        if (tmp.isPresent()){
            Exam exam = tmp.get();
            exam.setStatus("考试结束");
            System.out.println(2);
            examRepository.save(exam);
            modelAndView.setViewName("redirect:/tec/exam/" + exam.getClassId().getClassId());
        }
        return modelAndView;
    }
}