package com.atguigu.myssm.basedao;

import com.atguigu.myssm.ioc.BeanFactory;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;
    public DispatcherServlet(){}

    public void init()throws ServletException{
        super.init();
        //֮ǰ���ڴ˴���������IOC������
        //�����Ż�Ϊ��application������ȥ��ȡ
        //beanFactory=new ClassPathXmlApplicationContext();
        ServletContext application=getServletContext();
        Object beanFactoryObj=application.getAttribute("beanFactory");
        if(beanFactoryObj!=null){
            beanFactory=(BeanFactory) beanFactoryObj;
        }else{
            throw new RuntimeException("IOC������ȡʧ�ܣ�");
        }

}


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //���ñ���
        req.setCharacterEncoding("UTF-8");

        //����URL:http://localhost:8080/pro15/hello.do
        //��ôservletPath��:/hello.do
        //˼·��:
        //��һ��:/hello.do->hello
        //�ڶ���:hello->HelloController
        String servletPath=req.getServletPath();
        servletPath=servletPath.substring(1);
        int lastDotIndex=servletPath.lastIndexOf(".do");
        servletPath=servletPath.substring(0,lastDotIndex);

        Object controllerBeanObj=beanFactory.getBean(servletPath);

        String operate=req.getParameter("operate");
        if(operate==null||"".equals(operate)){
            operate="index";
        }
        try {
            Method[] methods=controllerBeanObj.getClass().getDeclaredMethods();
            for(Method method:methods){
                if(operate.equals(method.getName())) {
                    //1.ͳһ��ȡ�������

                    ////��ȡ��ǰ����������
                    Parameter[] parameters=method.getParameters();
                    //parameterValues�������ز�����ֵ
                    Object[] parameterValues=new Object[parameters.length];
                    for(int i=0;i<parameters.length;i++){
                        Parameter parameter=parameters[i];
                        String parameterName=parameter.getName();
                        if("req".equals(parameterName)){
                            parameterValues[i]=req;
                        }else if("resp".equals(parameterName)){
                            parameterValues[i]=resp;
                        }else if("session".equals(parameterName)){
                            parameterValues[i]=req.getSession();
                        }else{
                            //�������л�ȡ����ֵ
                            String parameterValue=req.getParameter(parameterName);
                            String typeName=parameter.getType().getName();

                            Object parameterObj=parameterValue;
                            if(parameterObj!=null){
                                if("java.lang.Integer".equals(typeName)){
                                    parameterObj=Integer.parseInt(parameterValue);
                                }
                            }
                            parameterValues[i]=parameterObj;
                        }

                    }

                    //2.controller����еķ�������
                    method.setAccessible(true);
                    Object returnObj=method.invoke(controllerBeanObj,parameterValues);

                    //3.��ͼ����
                    String methodReturnstr=(String)returnObj;
                    if(methodReturnstr.startsWith("redirect:")){
                        String redirectstr=methodReturnstr.substring("redirect:".length());
                        resp.sendRedirect(redirectstr);
                    }else{
                        super.processTemplate(methodReturnstr,req,resp);
                    }
                }
            }
        }

//               else{
////                throw new RuntimeException("operateֵ�Ƿ�!");
////            }

        catch (InvocationTargetException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
