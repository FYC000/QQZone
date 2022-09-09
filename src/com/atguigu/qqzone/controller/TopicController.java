package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.TopicService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TopicController {
    private TopicService topicService=null;
    public String topicDetail(Integer id, HttpSession session) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {

        /*
        //String-> java.util.Date
        String datestr1="2021-12-0 12:59:59";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1=sdf.parse(datestr1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Date->String
        Date date2=new Date();
        String dateStr2=sdf.format(date2);
        */

        Topic topic=topicService.getTopicById(id);
        session.setAttribute("topic",topic);
        return "frames/detail";
    }
    public String delTopic(Integer topicId,HttpSession session) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        topicService.delTopic(topicId);
        return "redirect:topic.do?operate=getTopicList";
    }
    public String getTopicList(HttpSession session) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        UserBasic userBasic=(UserBasic) session.getAttribute("userBasic");
        List<Topic>topicList=topicService.getTopicList(userBasic);
        userBasic.setTopicList(topicList);
        session.setAttribute("friend",userBasic);
        return "frames/main";
    }
}
