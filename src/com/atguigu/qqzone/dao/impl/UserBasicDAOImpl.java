package com.atguigu.qqzone.dao.impl;

import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.qqzone.dao.UserBasicDAO;
import com.atguigu.qqzone.pojo.UserBasic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UserBasicDAOImpl extends BaseDAO<UserBasic> implements UserBasicDAO {
    @Override
    public UserBasic getUserBasic(String loginId, String pwd) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        String sql="select* from t_user_basic where loginId=? && pwd=?";
        return super.load(sql,loginId,pwd);
    }

    @Override
    public List<UserBasic> getUserBasicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        String sql="select fid as id from t_friend where uid=?";
        return super.executeQuery(sql,userBasic.getId());
    }

    @Override
    public UserBasic getUserBasicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return super.load("select*from t_user_basic where id=?", id);
    }
}
