package com.example.releaselearning.android;

import cn.hutool.json.JSONUtil;
import com.example.releaselearning.entity.Homework;
import com.example.releaselearning.entity.HwDetail;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.repository.HomeworkRepository;
import com.example.releaselearning.repository.HwDetailRepository;
import com.example.releaselearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/android/homework")
public class homework {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private HwDetailRepository hwDetailRepository;

    @ResponseBody
    @GetMapping("/getUndoHomeWorkByStuId/{studentId}")
    public String getUndoHomeWorkByStuId(@PathVariable String studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        List<Homework> homeworkList = null;
        if (student.isPresent()){
            homeworkList = homeworkRepository.findHomeworkByClassId(student.get().getClassId());
            if(!(homeworkList.size() ==0)){
                for(Homework homework:homeworkList){
                    List<HwDetail> tmp = hwDetailRepository.findHwDetailsByHomeworkId(homework);
                    if(!(tmp.size() ==0)){
                        for(HwDetail temp:tmp){
                            if(temp.getStudentId().getStudentId().equals(studentId)){
                                if (!temp.getHomeworkStatus().equals("未完成")){
                                    homeworkList.remove(homework);
                                    if (homeworkList.size()==0){
                                        String msg = JSONUtil.toJsonStr(homeworkList);
                                        System.out.println(msg);
                                        return msg;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String msg = JSONUtil.toJsonStr(homeworkList);
        System.out.println(msg);
        return msg;
    }
    @ResponseBody
    @GetMapping("/getDidHomeWorkByStuId/{studentId}")
    public String getDidHomeWorkByStuId(@PathVariable String studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        List<Homework> homeworkList = null;
        if (student.isPresent()){
            homeworkList = homeworkRepository.findHomeworkByClassId(student.get().getClassId());
            if(!(homeworkList.size() ==0)){
                for(Homework homework:homeworkList){
                    List<HwDetail> tmp = hwDetailRepository.findHwDetailsByHomeworkId(homework);
                    if(!(tmp.size() ==0)){
                        for(HwDetail temp:tmp){
                            if(temp.getStudentId().getStudentId().equals(studentId)){
                                if (!temp.getHomeworkStatus().equals("已完成")){
                                    homeworkList.remove(homework);
                                    if (homeworkList.size()==0){
                                        String msg = JSONUtil.toJsonStr(homeworkList);
                                        System.out.println(msg);
                                        return msg;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String msg = JSONUtil.toJsonStr(homeworkList);
        System.out.println(msg);
        return msg;
    }

    @ResponseBody
    @GetMapping("/getHomeWorkByStudentIdAndHomeworkId/{studentId}/{homeworkId}")
    public String getHomeWorkByStudentIdAndHomeworkId(@PathVariable String studentId ,@PathVariable String homeworkId){
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Homework> homework = homeworkRepository.findById(homeworkId);
        Optional<HwDetail> hwDetail = hwDetailRepository.findHwDetailByStudentIdAndHomeworkId(student.get() , homework.get());

        String msg = "";

        if(hwDetail.isPresent()){
            msg = JSONUtil.toJsonStr(hwDetail.get());
        }
        return msg;
    }
}
