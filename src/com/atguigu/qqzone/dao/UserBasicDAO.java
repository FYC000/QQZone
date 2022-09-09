package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserBasicDAO {
    //�����˺ź������ȡ�ض��û���Ϣ
    UserBasic getUserBasic(String loginId,String pwd) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //��ȡָ���û������к����б�
    List<UserBasic> getUserBasicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //����id��ѯUserBasic����Ϣ
    UserBasic getUserBasicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
