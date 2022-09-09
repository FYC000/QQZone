package com.atguigu.qqzone.service.impl;

import com.atguigu.qqzone.dao.HostReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;
import com.atguigu.qqzone.service.HostReplyService;

import java.lang.reflect.InvocationTargetException;

public class HostReplyServiceImpl implements HostReplyService {
    private HostReplyDAO hostReplyDAO=null;
    @Override
    public HostReply getHostReplyById(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return hostReplyDAO.getHostReplyById(replyId);
    }

    @Override
    public void delHostReply(Integer Id) {
        hostReplyDAO.deiHostReply(Id);
    }
}
