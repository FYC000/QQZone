package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ReplyDAO {
    //获取所有回复列表信息
    List<Reply> getReplyList(Topic topic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //添加回复信息
    void addReply(Reply reply);
    //删除回复信息
    void delReply(Integer replyId);
    //获取指定回复信息
    Reply getReplyById(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
