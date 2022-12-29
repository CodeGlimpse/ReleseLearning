package com.example.releaselearning.controller;

import com.example.releaselearning.entity.*;
import com.example.releaselearning.repository.ExamDetailRepository;
import com.example.releaselearning.repository.ExamRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/stu")
public class StuExamController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamDetailRepository examDetailRepository;
    @Autowired
    private ExamRepository examRepository;
    @GetMapping("/exam/{studentId}")
    public String getExamPage(Map<String,Object> map, @PathVariable
            String studentId){
        return postHomeworkPage(map, studentId);
    }
    @PostMapping("/exam/{studentId}")
    public String postHomeworkPage(Map<String,Object> map, @PathVariable
            String studentId){
        //考试界面
        //查询该学生所在班级
       Optional<Student> now  = studentRepository.findById(studentId);

        //查询所有考试
        if(now.isPresent()){
            Student student = now.get();
            map.put("student", student);
            List<ExamDetail> allExam = examDetailRepository.findExamDetailByStudentId(student);
            map.put("allExam" , allExam);
        }

        return "stuExam";
    }


//考试详情在webrtc controller里
    //下载试卷
    @GetMapping("/exam/file/{id}")
    public String getOpenHomeworkFile(Map<String,Object> map,@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView();
        String page;
        Optional<ExamDetail> examDetail = examDetailRepository.findExamDetailById(id);
        Exam exam = examDetail.get().getExamId();
      //  Optional<HwDetail> hwDetail = hwDetailRepository.findHwDetailById(id);
        if(exam!=null){
            //测试使用的,已测试过这句话并不会新建一个文件，也并不会因为路径不存在而发生错误。
            // txt、word文件都已经测试过可以成功创建、复制并打开
            //File source = new File("E:\\12345.docx");
            //实际代码为
            File source = new File(exam.getExamContent());

            //1、创建本地待接收的远程文件。
            String path = "D:/"+ "/"+exam.getExamId() +"_" + examDetail.get().getStudentId().getStudentId()+"_" + source.getName();
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
                URL url = new URL("file:///"+exam.getExamContent());

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
            map.put("examDetail",examDetail.get());
            map.put("student",examDetail.get().getStudentId());
            page = "stuIndex";
          //  modelAndView.setViewName("redirect:/stu/exam/detail/" + examDetail.get().getExamId().getExamId()+"/"+examDetail.get().getExamId().getStatus()+"/"+examDetail.get().getStudentId().getStudentId());
        }else{
            page = "error";
            modelAndView.setViewName("redirect:/error");
        }
        return page;
       // return modelAndView;
    }


//    文件上传交卷
    @PostMapping("/exam/fileUpload")
    public ModelAndView fileUpload(@RequestParam("uploadFiles") MultipartFile multipartFiles, HttpServletRequest request){
        String examId = request.getParameter("examId");
        String studentId = request.getParameter("studentId");
        int id = 1;
        System.out.println(examId);
        System.out.println(studentId);

        //文件下载
        //获取日期
        String uploadDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //设置文件保存路径，为运行目录下的uploadFile文件夹
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() +
                "static/uploadFile/exam/" + examId + "/" + studentId + "/" + uploadDate;
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
                    request.getServerPort() + "/uploadFile/exam/" + examId + "/" + studentId + "/" + uploadDate + "/" + newName;
            System.out.println(fpath);
        }catch (Exception e){
            e.printStackTrace();
        }

        //将fpath存储进数据库
        Optional<Exam> temp = examRepository.findById(examId);
        Exam exam = temp.get();
        if(temp.isPresent() && fpath.length() != 0){
            System.out.println(exam);
            List<ExamDetail> examDetails = examDetailRepository.findExamDetailsByExamId(exam);
            for (ExamDetail e : examDetails){
                if(e.getStudentId().getStudentId().equals(studentId)){
                    e.setExamFile(fpath);
                    e.setExamScore(-1);
                    examDetailRepository.save(e);
                    System.out.println(e);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();

        if (exam.getStatus().equals("正在考试")){
            List<ExamDetail> examDetailes = examDetailRepository.findExamDetailsByExamId(exam);
            for (ExamDetail e : examDetailes){
                if(e.getStudentId().getStudentId().equals(studentId)){
                    id = e.getId();
                    System.out.println(e);
                }
            }
            modelAndView.setViewName("redirect:/webrtc/exam/detail/"+ id+"/"+studentId);
        } else if (exam.getStatus().equals("考试结束")) {
            modelAndView.setViewName("redirect:/stu/exam/" + studentId);
        }

        return modelAndView;


    }


}
