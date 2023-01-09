package com.lmq.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/28 19:30
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    //拦截web包下任何类中任何方法
    @Pointcut("execution(* com.lmq.web.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest(); //拿到request
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获取类名方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        //输出
        logger.info("Request {}",requestLog);
    }
    @After("log()")
    public void doAfter(){
        logger.info("===============doAfter==================");
    }

    //拦截方法执行完返回的时候
    @AfterReturning(returning = "res",pointcut = "log()")
    public void doAfterReturn(Object res){
        logger.info("Result : {}" , res);
    }


    //封装到内部类
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args; //请求的参数

        public RequestLog() {
        }

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }




}
