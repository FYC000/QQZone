package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ReplyDAO {
    //��ȡ���лظ��б���Ϣ
    List<Reply> getReplyList(Topic topic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //��ӻظ���Ϣ
    void addReply(Reply reply);
    //ɾ���ظ���Ϣ
    void delReply(Integer replyId);
    //��ȡָ���ظ���Ϣ
    Reply getReplyById(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
