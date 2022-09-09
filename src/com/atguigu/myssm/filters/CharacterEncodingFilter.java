package com.atguigu.myssm.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.do"},initParams = {@WebInitParam(name="encoding",value="UTF-8")})

public class CharacterEncodingFilter implements Filter {
    private String encoding="UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingStr=filterConfig.getInitParameter("encoding");
        if(encodingStr!=null&&!"".equals(encodingStr)){
            encoding=encodingStr;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //…Ë÷√±‡¬Î
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}
