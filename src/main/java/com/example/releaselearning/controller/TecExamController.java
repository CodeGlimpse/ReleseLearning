package com.example.releaselearning.controller;

import com.example.releaselearning.entity.*;
import com.example.releaselearning.entity.Class;
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

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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

    //点击详情到查看考试界面
    @GetMapping("/exam/detail/{examId}")
    public String getExamDetailPage(Map<String, Object> map, @PathVariable String examId) {
        return postExamDetailPage(map,examId);
    }
    @PostMapping("/exam/detail/{examId}")
    public String postExamDetailPage(Map<String,Object> map,@PathVariable String examId){

        Optional<Exam> exam = examRepository.findById(examId);
        System.out.println(examId);
        if(exam.isPresent()){
            System.out.println("nihao");
            List<ExamDetail> exams = examDetailRepository.findExamDetailsByExamId(exam.get());
            map.put("exams",exams);
            map.put("examId",examId);
        }

        return "tecExamDetail";
    }

    //修改分数
    @PostMapping("/examScore/{id}")
    public ModelAndView updateExamScore(@PathVariable int id, String examScore){
        examDetailRepository.updateExamDetailsById(Integer.valueOf(examScore),id);
        Optional<ExamDetail> examDetail = examDetailRepository.findExamDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tec/exam/detail/" + examDetail.get().getExamId().getExamId());
        return modelAndView;
    }

    //打开文件
    @GetMapping("/exam/file/{id}")
    public ModelAndView getOpenHomeworkFile(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<ExamDetail> examDetail = examDetailRepository.findExamDetailById(id);
        if(examDetail.isPresent()){
            //测试使用的,已测试过这句话并不会新建一个文件，也并不会因为路径不存在而发生错误。
            // txt、word文件都已经测试过可以成功创建、复制并打开
            //File source = new File("E:\\12345.docx");
            //实际代码为
            File source = new File(examDetail.get().getExamFile());

            //1、创建本地待接收的远程文件。
            String path = "D:/"+ "/"+examDetail.get().getExamId().getExamId() +"_" + examDetail.get().getStudentId().getStudentId()+"_" + source.getName();
            File f = new File(path);
            try {
                System.out.println(f.getAbsolutePath());
                if(!f.exists()){
                    f.createNewFile();
                }
                //2、创建本地至服务器端的远程连接
                FileOutputStream fileOutputStream = new FileOutputStream(f);
                byte[] pb = new byte[1024];

                //测试用的代码
                //URL url = new URL("file:///D:/libingyu.docx");
                //实际项目代码
                URL url = new URL(examDetail.get().getExamFile());

                URLConnection urlc = url.openConnection();
                InputStream inputStream = urlc.getInputStream();
                //3、将服务器端文件写入输入流，从输入流中将内容写入本地文件，完成文件下载。
                int length = -1;
                while (true){
                    length = inputStream.read(pb);
                    if(length<0){
                        fileOutputStream.flush();
                        break;
                    }else {
                        fileOutputStream.write(pb,0,length);
                    }
                }
                inputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //打开文件
            if(Desktop.isDesktopSupported()){
                System.out.println("Desktop is supported");
                Desktop desktop = Desktop.getDesktop();
                //File f = new File("D:\\key.txt");
                if(f.exists()){
                    System.out.println("wenjianyou");
                    try {
                        desktop.open(f);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            modelAndView.setViewName("redirect:/tec/exam/detail/" + examDetail.get().getExamId().getExamId());
        }else{
            modelAndView.setViewName("redirect:/error");
        }
        return modelAndView;
    }

//教师监考
    @GetMapping("exam/vcs/{exam_status}/{exam_id}")
    public ModelAndView examDetail(@PathVariable String exam_status,@PathVariable String exam_id){
        return postExamDetail(exam_status,exam_id);
    }

    @PostMapping("exam/vcs/{exam_status}/{exam_id}")
    public ModelAndView postExamDetail(@PathVariable String exam_status,@PathVariable String exam_id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Exam> tmp = examRepository.findById(exam_id);
        Exam exam = tmp.get();
        if (exam_status.equals("正在考试")) {
            modelAndView.setViewName("redirect:/webrtc");
        }
        return modelAndView;
    }


}