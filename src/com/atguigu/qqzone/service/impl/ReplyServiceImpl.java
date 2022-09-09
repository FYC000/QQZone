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
    * 此处引入的是其他POJO对应的Service接口，而不是DAO接口
    * 其他POJO对应的业务逻辑是封装在service层的，只需要调用别人的业务业务逻辑方法，而不要去深入考虑人家内部的细节
    * */
    private ReplyDAO replyDAO=null;
    private HostReplyService hostReplyService=null;
    private UserBasicService userBasicService=null;
    @Override
    public List<Reply> getReplyList(Integer topicId) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        List<Reply>replyList=replyDAO.getReplyList(new Topic(topicId));
        for(int i=0;i<replyList.size();i++){
            Reply reply=replyList.get(i);
            //将关联的作者设置进去
            UserBasic author=reply.getAuthor();
            author=userBasicService.getUserBasicById(author.getId());
            reply.setAuthor(author);

            //将关联的HostReply设置进去
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
        //根据id获取指定回复信息
        Reply reply=replyDAO.getReplyById(replyId);

        if(reply!=null){
            //如果reply有关联的hostReply，则先删除，防止因为外键原因导致无法删除reply的信息
            HostReply hostReply=hostReplyService.getHostReplyById(replyId);
            if(hostReply!=null){
                hostReplyService.delHostReply(hostReply.getId());
            }
            //删除reply信息
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
