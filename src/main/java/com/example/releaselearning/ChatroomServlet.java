package com.example.releaselearning;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.releaselearning.entity.Class;
import com.example.releaselearning.entity.Student;
import com.example.releaselearning.entity.Teacher;
import com.example.releaselearning.repository.ClassRepository;
import com.example.releaselearning.repository.StudentRepository;
import com.example.releaselearning.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName WebSocketConfig
 * @Description TODO
 * @Author zrc
 * @Date 11:01
 * @Version 1.0
 **/
@Component
@Slf4j
@ServerEndpoint(value ="/websocket/{number}/{userId}")
public class ChatroomServlet {

    //实例一个session，这个session是websocket的session
    private Session session;

    //存放当前用户名
    private String userName;

    //存放在线的用户数量
    private static Integer userNumber = 0;

    //存放websocket的集合（本次demo不会用到，聊天室的demo会用到）
    private static CopyOnWriteArraySet<ChatroomServlet> webSocketSet = new CopyOnWriteArraySet<>();


    private static ApplicationContext applicationContext;
    //要注入的bean
    private StudentRepository studentRepository;
    private static TeacherRepository teacherRepository;
    private static ClassRepository classRepository;
    //在主入口中注入applicationContext
    public static void setApplicationContext(ApplicationContext applicationContext){
        ChatroomServlet.applicationContext = applicationContext;
    }

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session,@PathParam("number") String number,@PathParam("userId") String userId) throws IOException {
        List<String> alluserLists = new ArrayList<>();
        this.studentRepository = (StudentRepository) applicationContext.getBean("studentRepository");
        this.teacherRepository = (TeacherRepository) applicationContext.getBean("teacherRepository");
        this.classRepository = (ClassRepository) applicationContext.getBean("classRepository");
        if(number.equals("0")) {
            //获取当前用户
            Optional<Student> now = studentRepository.findById(userId);
            Student student = now.get();

            //获得所有用户
            List<Student> studentLists = studentRepository.findStudentByClassId(student.getClassId());
            Class aclass = classRepository.findById(student.getClassId().getClassId()).get();
            Teacher teacher = teacherRepository.findById(aclass.getTeacherId().getTeacherId()).get();

            alluserLists.add(teacher.getName() + "老师");
            for (Student student1 : studentLists) {
                alluserLists.add(student1.getName());
            }
            this.userName = student.getName();
        }else{
            //获取当前用户
            Optional<Class> now = classRepository.findById(userId);
            Class aclass = now.get();
            Teacher teacher = teacherRepository.findById(aclass.getTeacherId().getTeacherId()).get();
            List<Student> studentLists = studentRepository.findStudentByClassId(aclass);

            alluserLists.add(teacher.getName() + "老师");
            for (Student student1 : studentLists) {
                alluserLists.add(student1.getName());
            }
            this.userName = teacher.getName()+"老师";

        }


        this.session = session;
        //将当前对象放入webSocketSet
        webSocketSet.add(this);
        //增加在线人数
        userNumber++;

        //在线用户
        Set<String> userLists = new TreeSet<>();
        for (ChatroomServlet chatroomServlet : webSocketSet) {
            userLists.add(chatroomServlet.userName);
        }
        //不在线用户
        Set<String> nouserLists = new TreeSet<>();
        for(String user : alluserLists){
            if(!userLists.contains(user)){
                nouserLists.add(user);
            }
        }

        //将所有信息包装好传到客户端(给所有用户)
        Map<String, Object> map1 = new HashMap();
        //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息 5代表未上线用户
        map1.put("messageType", 1);
        //  返回用户名
        map1.put("userName", userName);
        //  返回在线人数
        map1.put("number", this.userNumber);
        //发送给所有用户谁上线了，并让他们更新自己的用户菜单
        sendMessageAll(JSON.toJSONString(map1),this.userName);
        log.info("【websocket消息】有新的连接, 总数:{}", this.userNumber);


        // 更新在线人数(给所有人)
        Map<String, Object> map2 = new HashMap();
        //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息 5表示不在线
        map2.put("messageType", 3);
        // 返回用户名
        map2.put("userName", userName);
        //把所有在线用户放入map2
        map2.put("onlineUsers", userLists);
        //返回在线人数
        map2.put("number", this.userNumber);
        // 消息发送指定人（所有的在线用户信息）
        sendMessageAll(JSON.toJSONString(map2),this.userName);

        // 更新不在线人数(给所有人)
        Map<String, Object> map3 = new HashMap();
        //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息 5表示不在线
        map3.put("messageType", 5);
        // 返回用户名
        map3.put("userName", userName);
        //把不在线用户放入map2
        map3.put("onlineUsers", nouserLists);
        // 消息发送指定人（所有的在线用户信息）
        sendMessageAll(JSON.toJSONString(map3),this.userName);
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() throws IOException {
        //从集合中移除当前对象
        webSocketSet.remove(this);
        //在线用户数减少
        userNumber--;
        Map<String, Object> map1 = new HashMap();
        //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息 5表示不在线
        map1.put("messageType", 2);
        //所有在线用户
        map1.put("onlineUsers", this.webSocketSet);
        //下线用户的用户名
        map1.put("studentName", this.userName);
        //返回在线人数
        map1.put("number", userNumber);
        //发送信息，所有人，通知谁下线了
        sendMessageAll(JSON.toJSONString(map1),this.userName);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }


//    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
        //将前端传来的数据进行转型
        JSONObject jsonObject = JSON.parseObject(message);
        //获取所有数据
        String textMessage = jsonObject.getString("message");
        String usernameid = jsonObject.getString("usernameid");
        String number = jsonObject.getString("number");
        String username=null;
        if (number.equals("0")) {
            Optional<Student> now = studentRepository.findById(usernameid);
            Student student = now.get();
            username = student.getName();
        }else {
            Optional<Class> now =classRepository.findById(usernameid);
            Class aclass=now.get();
            Teacher teacher=teacherRepository.findById(aclass.getTeacherId().getTeacherId()).get();
            username = teacher.getName()+"老师";
        }
        //群发信息
        Map<String, Object> map1 = new HashMap();
        map1.put("messageType", 4);
        //所有在线用户
        map1.put("onlineUsers", this.webSocketSet);
        //发送消息的用户名
        map1.put("userName", username);
        //发送消息的用户名id
        map1.put("usernameid", usernameid);
        //返回在线人数
        map1.put("number", userNumber);
        //发送的消息
        map1.put("textMessage", textMessage);
        //发送信息，所有人，通知谁下线了
        sendMessageAll(JSON.toJSONString(map1),this.userName);

    }
//    /**
//     *  消息发送所有人
//     */
    public void sendMessageAll(String message,String userName) throws IOException {
        for (ChatroomServlet webSocket: webSocketSet) {
            //消息发送所有人（同步）getAsyncRemote
            webSocket.session.getBasicRemote().sendText(message);
        }
    }
}
