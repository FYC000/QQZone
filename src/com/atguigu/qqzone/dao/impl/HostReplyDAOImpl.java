package com.atguigu.qqzone.dao.impl;

import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.qqzone.dao.HostReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;

import java.lang.reflect.InvocationTargetException;

public class HostReplyDAOImpl extends BaseDAO<HostReply> implements HostReplyDAO {
    @Override
    public HostReply getHostReplyById(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return super.load("select* from t_host_reply where reply=?",replyId);
    }

    @Override
    public void deiHostReply(Integer Id) {
        super.executeUpdate("delete from t_host_reply where id=?",Id);
    }
}
