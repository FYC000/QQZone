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
        //之前是在此处主动创建IOC容器的
        //现在优化为从application作用域去获取
        //beanFactory=new ClassPathXmlApplicationContext();
        ServletContext application=getServletContext();
        Object beanFactoryObj=application.getAttribute("beanFactory");
        if(beanFactoryObj!=null){
            beanFactory=(BeanFactory) beanFactoryObj;
        }else{
            throw new RuntimeException("IOC容器获取失败！");
        }

}


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置编码
        req.setCharacterEncoding("UTF-8");

        //假设URL:http://localhost:8080/pro15/hello.do
        //那么servletPath是:/hello.do
        //思路是:
        //第一步:/hello.do->hello
        //第二步:hello->HelloController
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
                    //1.统一获取请求参数

                    ////获取当前方法的数组
                    Parameter[] parameters=method.getParameters();
                    //parameterValues用来承载参数的值
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
                            //从请求中获取参数值
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

                    //2.controller组件中的方法调用
                    method.setAccessible(true);
                    Object returnObj=method.invoke(controllerBeanObj,parameterValues);

                    //3.视图处理
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
////                throw new RuntimeException("operate值非法!");
////            }

        catch (InvocationTargetException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
