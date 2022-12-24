package com.example.releaselearning.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.HwDetail;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.HwDetailRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/stu")
public class StuHomeworkController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private HwDetailRepository hwDetailRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;

    @GetMapping("/homework/{studentId}")
    public String getHomeworkPage(Map<String, Object> map, @PathVariable
            String studentId) {
        return postHomeworkPage(map, studentId);
    }

    @PostMapping("/homework/{studentId}")
    public String postHomeworkPage(Map<String, Object> map, @PathVariable
            String studentId) {
        //作业界面
        //查询该学生所在班级
        Optional<Student> now = studentRepository.findById(studentId);
        //查询所有作业
        if(now.isPresent()) {
            Student student = now.get();
            map.put("student", student);
            List<HwDetail> allHomework = hwDetailRepository.findHwDetailByStudentId(student);
            System.out.println(allHomework.size());
            map.put("allHomework", allHomework);
        }
        return "stuHomework";
    }

    @GetMapping("/homework/findOne/{homeworkId}/{studentId}")
    public String getOneHomework(Map<String,Object> map, @PathVariable
    String homeworkId, @PathVariable String studentId){
        return postOneHomework(map, homeworkId, studentId);
    }

    @PostMapping("/homework/findOne/{homeworkId}/{studentId}")
    public String postOneHomework(Map<String,Object> map, @PathVariable
    String homeworkId, @PathVariable String studentId) {
        //单个作业提交
        Optional<Homework> nowHomework = homeworkRepository.findById(homeworkId);
        if(nowHomework.isPresent()){
            Homework homework = nowHomework.get();
            map.put("homeworkContent", homework);
            List<HwDetail> hwDetails = hwDetailRepository.findHwDetailsByHomeworkId(homework);
            for (HwDetail h : hwDetails){
                if(h.getStudentId().getStudentId().equals(studentId)){
                    map.put("homeworkDetail", h);
                }
            }
        }

        return "enter";
    }

    @PostMapping("/fileUpload")
    public ModelAndView fileUpload(@RequestParam("uploadFiles") MultipartFile multipartFiles, HttpServletRequest request){
        String homeworkId = request.getParameter("homeworkId");
        String studentId = request.getParameter("studentId");
        System.out.println(homeworkId);
        System.out.println(studentId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/stu/homework/" + studentId);
        //文件下载
        //获取日期
        String uploadDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //设置文件保存路径，为运行目录下的uploadFile文件夹
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() +
                "static/uploadFile/" + uploadDate;
        File file = new File(path);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        //文件重命名，避免重名
        String oldName = multipartFiles.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        String fpath = "";
        try{
            File file1 = new File(file, newName);
            multipartFiles.transferTo(file1);
            //生成上传文件的访问路径
            fpath = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + "/uploadFile/" + uploadDate + "/" + newName;
            System.out.println(fpath);
        }catch (Exception e){
            e.printStackTrace();
        }

        //将fpath存储进数据库
        Optional<Homework> nowHomework = homeworkRepository.findById(homeworkId);
        if(nowHomework.isPresent() && fpath.length() != 0){
            Homework homework = nowHomework.get();
            System.out.println(homework);
            List<HwDetail> hwDetails = hwDetailRepository.findHwDetailsByHomeworkId(homework);
            for (HwDetail h : hwDetails){
                if(h.getStudentId().getStudentId().equals(studentId)){
                    h.setHomeworkFile(fpath);
                    h.setHomeworkStatus("已完成");
                    hwDetailRepository.save(h);
                    System.out.println(h);
                }
            }
        }

        return modelAndView;


    }

}
