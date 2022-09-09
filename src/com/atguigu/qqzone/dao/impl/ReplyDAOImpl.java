package com.atguigu.qqzone.dao.impl;

import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.qqzone.dao.ReplyDAO;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ReplyDAOImpl extends BaseDAO<Reply> implements ReplyDAO {
    @Override
    public List<Reply> getReplyList(Topic topic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return super.executeQuery("select*from t_reply where topic=?",topic.getId());
    }

    @Override
    public void addReply(Reply reply) {
        super.executeUpdate("insert into t_reply values(0,?,?,?,?)",reply.getContent(),reply.getReplyDate(),reply.getAuthor().getId(),reply.getTopic().getId());
    }

    @Override
    public void delReply(Integer replyId) {
        super.executeUpdate("delete from t_reply where id=?",replyId);
    }

    @Override
    public Reply getReplyById(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return super.load("select*from t_reply where id=?",replyId);
    }
}
