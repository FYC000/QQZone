package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserBasicDAO {
    //根据账号和密码获取特定用户信息
    UserBasic getUserBasic(String loginId,String pwd) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //获取指定用户的所有好友列表
    List<UserBasic> getUserBasicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    //根据id查询UserBasic的信息
    UserBasic getUserBasicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
