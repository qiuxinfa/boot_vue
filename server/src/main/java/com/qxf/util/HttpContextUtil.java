package com.qxf.util;

/**
 * @ClassName HttpContextUtil
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/5/31 19:47
 **/

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtil {
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}