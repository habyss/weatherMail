package com.test.demo.annotation;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kun.han
 */
@Aspect
@Component
public class RequestLimitContract {

    @Before("@annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) {
        System.out.println("limit - count" + limit.count() + " time" + limit.time());
        String ip = getIp();
        System.out.println(ip);
    }

//    @Before("within(com.test.demo.controller.*)")
//    public void requestLimit(final JoinPoint joinPoint){
//        System.out.println("limit - count" + 1 + " time" + 2);
//        String ip = getIp();
//        System.out.println(ip);
//    }

    @Around("@annotation(limit)")
    public void test(final ProceedingJoinPoint joinPoint, RequestLimit limit) throws Throwable {
        System.out.println("around - " + System.currentTimeMillis());
        joinPoint.proceed();
        System.out.println("around - " + System.currentTimeMillis());
    }

    private String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");

        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
