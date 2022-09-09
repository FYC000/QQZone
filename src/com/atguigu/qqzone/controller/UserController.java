package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.TopicService;
import com.atguigu.qqzone.service.UserBasicService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UserController {
    private UserBasicService userBasicService = null;
    private TopicService topicService = null;

    public String login(String loginId, String pwd, HttpSession session) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        UserBasic userBasic = userBasicService.login(loginId, pwd);

        if (userBasic != null) {
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            List<Topic> topicList = topicService.getTopicList(userBasic);

            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);

            session.setAttribute("userBasic", userBasic);
            session.setAttribute("friend",userBasic);
            return "index";
        } else {
            return "login";
        }
    }

    //当用户进入好友的页面时，对session中的friend的值进行修改
    public String friend(Integer id, HttpSession session) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {

        //获取当前好友的UserBasic类
        UserBasic currFriend= userBasicService.getUserBasicById(id);
        List<Topic>topicList=topicService.getTopicList(currFriend);

        currFriend.setTopicList(topicList);

        //通过在session中储存friend值，用于判断是否为用户自己的空间作为判断条件
        session.setAttribute("friend",currFriend);
        return "index";
    }

}
