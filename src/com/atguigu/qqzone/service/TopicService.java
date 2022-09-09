package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface TopicService {
    //查询特定用户的日志列表
    List<Topic> getTopicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //查询指定的日志信息
    Topic getTopicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //删除指定的日志信息
    void delTopic(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
