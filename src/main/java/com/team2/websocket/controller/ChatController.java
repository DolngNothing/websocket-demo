package com.team2.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@EnableScheduling
@RestController
public class ChatController {

    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate; //@注解1
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
    //映射客户端"/hello"请求
    @MessageMapping(value = "/hello") //@注解2
    //向订阅了"/topic/hello"主题的客户端广播消息
    @SendTo(value = "/topic/hello") //@注解3
    public String reponseMsg(String msg) {  //msg->客户端传来的消息
        return msg+"world";
    }

    @Scheduled(fixedRate = 1000*10) //设置定时器，每隔10s主动向客户端(订阅了"/topic/hello"主题)发送消息
    @SendTo("/topic/hello")
    public void scheduleSendMsg() {
        Date now = new Date();
        //发送消息
        messagingTemplate.convertAndSend("/topic/hello", df.format(now).toString());//@注解4
        logger.info(now.toString());
    }
    //点对点通信
    @Scheduled(fixedRate = 1000*10)
    public void scheduleSendMsgToUser() {
        Date now = new Date();
        int userId = 1;
        //@注解5
        messagingTemplate.convertAndSendToUser(userId+"","/queue/hello", df.format(now).toString());
        logger.info(now.toString());
    }
}