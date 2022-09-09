package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface TopicDAO {
    //��ȡָ���û����б���Ϣ
    List<Topic>getTopicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //�����־
    void addTopic(Topic topic);
    //ɾ����־
    void delTopic(Integer id);
    //��ȡָ����־��Ϣ
    Topic getTopic(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
