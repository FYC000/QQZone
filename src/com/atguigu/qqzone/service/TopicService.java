package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface TopicService {
    //��ѯ�ض��û�����־�б�
    List<Topic> getTopicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //��ѯָ������־��Ϣ
    Topic getTopicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //ɾ��ָ������־��Ϣ
    void delTopic(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
