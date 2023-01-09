package com.lmq.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/28 17:33
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    //获取日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());



    //拦截器
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        //找到哪个路径出现的异常 记录异常信息
        logger.error("Request URL : {} , Exception : {}",request.getRequestURL(),e);

        //不拦截一些指定的错误
        if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
            throw e;
        }


        ModelAndView mv = new ModelAndView();
        //获取URL
        mv.addObject("url",request.getRequestURL());
        //获取异常信息
        mv.addObject("exception",e);
        mv.setViewName("error/error");

        return mv;
    }


}
