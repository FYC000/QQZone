package com.atguigu.qqzone.service.impl;

import com.atguigu.qqzone.dao.UserBasicDAO;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.UserBasicService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {
    private UserBasicDAO userBasicDAO=null;
    @Override
    public UserBasic login(String loginId, String pwd) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        UserBasic userBasic=userBasicDAO.getUserBasic(loginId,pwd);
        return userBasic;
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        List<UserBasic>userBasicList=userBasicDAO.getUserBasicList(userBasic);
        List<UserBasic>friendList=new ArrayList<>(userBasicList.size());
        for(int i=0;i<userBasicList.size();i++){
            UserBasic friend=userBasicList.get(i);
            friend=userBasicDAO.getUserBasicById(friend.getId());
            friendList.add(friend);
        }

        return friendList;
    }

    @Override
    public UserBasic getUserBasicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return userBasicDAO.getUserBasicById(id);
    }

}
