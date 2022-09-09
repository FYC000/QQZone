package com.atguigu.qqzone.service.impl;

import com.atguigu.qqzone.dao.ReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.HostReplyService;
import com.atguigu.qqzone.service.ReplyService;
import com.atguigu.qqzone.service.UserBasicService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ReplyServiceImpl implements ReplyService {
    /*
    * �˴������������POJO��Ӧ��Service�ӿڣ�������DAO�ӿ�
    * ����POJO��Ӧ��ҵ���߼��Ƿ�װ��service��ģ�ֻ��Ҫ���ñ��˵�ҵ��ҵ���߼�����������Ҫȥ���뿼���˼��ڲ���ϸ��
    * */
    private ReplyDAO replyDAO=null;
    private HostReplyService hostReplyService=null;
    private UserBasicService userBasicService=null;
    @Override
    public List<Reply> getReplyList(Integer topicId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        List<Reply>replyList=replyDAO.getReplyList(new Topic(topicId));
        for(int i=0;i<replyList.size();i++){
            Reply reply=replyList.get(i);
            //���������������ý�ȥ
            UserBasic author=reply.getAuthor();
            author=userBasicService.getUserBasicById(author.getId());
            reply.setAuthor(author);

            //��������HostReply���ý�ȥ
            HostReply hostReply=hostReplyService.getHostReplyById(reply.getId());
            reply.setHostReply(hostReply);
        }
        return replyList;
    }

    @Override
    public void addReply(Reply reply) {
        replyDAO.addReply(reply);
    }
    public void delReply(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        //����id��ȡָ���ظ���Ϣ
        Reply reply=replyDAO.getReplyById(replyId);

        if(reply!=null){
            //���reply�й�����hostReply������ɾ������ֹ��Ϊ���ԭ�����޷�ɾ��reply����Ϣ
            HostReply hostReply=hostReplyService.getHostReplyById(replyId);
            if(hostReply!=null){
                hostReplyService.delHostReply(hostReply.getId());
            }
            //ɾ��reply��Ϣ
            replyDAO.delReply(replyId);
        }

    }

    @Override
    public void delReplyList(Integer replyId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        List<Reply> replyList=getReplyList(replyId);
        for(Reply reply:replyList){
            delReply(reply.getId());
        }
    }
}
