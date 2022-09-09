package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.HostReply;

import java.lang.reflect.InvocationTargetException;

public interface HostReplyService {
    HostReply getHostReplyById(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    void delHostReply(Integer Id);
}
