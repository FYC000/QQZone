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

    //���û�������ѵ�ҳ��ʱ����session�е�friend��ֵ�����޸�
    public String friend(Integer id, HttpSession session) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {

        //��ȡ��ǰ���ѵ�UserBasic��
        UserBasic currFriend= userBasicService.getUserBasicById(id);
        List<Topic>topicList=topicService.getTopicList(currFriend);

        currFriend.setTopicList(topicList);

        //ͨ����session�д���friendֵ�������ж��Ƿ�Ϊ�û��Լ��Ŀռ���Ϊ�ж�����
        session.setAttribute("friend",currFriend);
        return "index";
    }

}
