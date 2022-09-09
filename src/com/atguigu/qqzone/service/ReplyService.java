package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Reply;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ReplyService {
    List<Reply> getReplyList(Integer topicId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    void addReply(Reply reply);
    void delReply(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    void delReplyList(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
