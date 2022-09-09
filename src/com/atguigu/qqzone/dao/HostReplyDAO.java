package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.HostReply;

import java.lang.reflect.InvocationTargetException;

public interface HostReplyDAO {
    HostReply getHostReplyById(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    void deiHostReply(Integer replyId);
}
