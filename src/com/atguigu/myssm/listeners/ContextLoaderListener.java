package com.atguigu.myssm.listeners;

import com.atguigu.myssm.ioc.BeanFactory;
import com.atguigu.myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//������������������������������ʱ��ȥ����IOC������Ȼ���䱣�浽application������
//��������������ٴ�application�������л�ȡIOC����
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application=servletContextEvent.getServletContext();
        String path=application.getInitParameter("contextConfigLocation");
        BeanFactory beanFactory=new ClassPathXmlApplicationContext(path);

        application.setAttribute("beanFactory",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
