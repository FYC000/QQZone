package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserBasicService  {
    UserBasic login(String loginId,String pwd) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    List<UserBasic>getFriendList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
    UserBasic getUserBasicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException;
}
