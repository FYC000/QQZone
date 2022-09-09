package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface TopicDAO {
    //获取指定用户的列表信息
    List<Topic>getTopicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //添加日志
    void addTopic(Topic topic);
    //删除日志
    void delTopic(Integer id);
    //获取指定日志信息
    Topic getTopic(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
