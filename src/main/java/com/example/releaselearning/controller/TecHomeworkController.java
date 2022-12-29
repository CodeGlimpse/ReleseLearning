package com.example.releaselearning.controller;

import com.example.releaselearning.entity.*;
import com.example.releaselearning.entity.Class;
import com.example.releaselearning.repository.*;
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

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    //作业详情-李冰玉
    @GetMapping("/homework/detail/{homework_id}")
    public String postHomeworkDetail(Map<String,Object> map,@PathVariable String homework_id){
        Optional<Homework> homework = homeworkRepository.findById(homework_id);
        List<HwDetail> hwDetails = null;
        if(homework.isPresent()){
            hwDetails = hwDetailRepository.findHwDetailsByHomeworkId(homework.get());
            map.put("hwDetails",hwDetails);
            map.put("homeworkId",homework_id);
        }
        return "tecHomeworkDetail";
    }

    //修改分数
    @PostMapping("/homeworkScore/{id}")
    public ModelAndView updateHomeworkScore(@PathVariable int id, String homeworkScore){
        hwDetailRepository.updateHwDetailsById(Integer.valueOf(homeworkScore),id);
        Optional<HwDetail> hwDetail = hwDetailRepository.findHwDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/tec/homework/detail/" + hwDetail.get().getHomeworkId().getHomeworkId());
        return modelAndView;
    }

    //打开文件
    @GetMapping("/homework/file/{id}")
    public ModelAndView getOpenHomeworkFile(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<HwDetail> hwDetail = hwDetailRepository.findHwDetailById(id);
        if(hwDetail.isPresent()){
            //测试使用的,已测试过这句话并不会新建一个文件，也并不会因为路径不存在而发生错误。
            // txt、word文件都已经测试过可以成功创建、复制并打开
            //File source = new File("E:\\12345.docx");
            //实际代码为
            File source = new File(hwDetail.get().getHomeworkFile());

            //1、创建本地待接收的远程文件。
            String path = "D:/"+ "/"+hwDetail.get().getHomeworkId().getHomeworkId() +"_" + hwDetail.get().getStudentId().getStudentId()+"_" + source.getName();
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
//            URL url = new URL("file:///D:/libingyu.docx");
            //实际项目代码
            URL url = new URL("file:///"+hwDetail.get().getHomeworkFile());

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
            modelAndView.setViewName("redirect:/tec/homework/detail/" + hwDetail.get().getHomeworkId().getHomeworkId());
        }else{
            modelAndView.setViewName("redirect:/error");
        }
        return modelAndView;
    }



}








