package com.atguigu.qqzone.service.impl;

import com.atguigu.qqzone.dao.TopicDAO;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.ReplyService;
import com.atguigu.qqzone.service.TopicService;
import com.atguigu.qqzone.service.UserBasicService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TopicServiceImpl implements TopicService {
    private TopicDAO topicDAO=null;
    private ReplyService replyService=null;
    private UserBasicService userBasicService=null;
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        return topicDAO.getTopicList(userBasic);
    }

    @Override
    public Topic getTopicById(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        Topic topic=topicDAO.getTopic(id);
        UserBasic author=topic.getAuthor();
        author=userBasicService.getUserBasicById(author.getId());
        topic.setAuthor(author);
        List<Reply>replyList=replyService.getReplyList(topic.getId());
        topic.setReplyList(replyList);
        return topic;
    }

    @Override
    public void delTopic(Integer id) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        Topic topic=topicDAO.getTopic(id);
        if(topic!=null){
            if(replyService.getReplyList(id)!=null){
                replyService.delReplyList(id);
            }
            topicDAO.delTopic(id);
        }
    }
}
