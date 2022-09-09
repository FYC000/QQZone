package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

public class ReplyController {
    ReplyService replyService=null;
    public String addReply(String content, Integer topicId, HttpSession session){
        UserBasic userBasic=(UserBasic)session.getAttribute("userBasic");
        Reply reply=new Reply(content,LocalDateTime.now(),userBasic,new Topic(topicId));
        replyService.addReply(reply);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }
    public String delReply(Integer replyId,Integer topicId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        replyService.delReply(replyId);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }
}
