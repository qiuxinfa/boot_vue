package com.qxf.util;

import com.qxf.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName UserUtil
 * @Description 获取当前登录的用户信息
 * @Author qiuxinfa
 * @Date 2020/8/9 21:50
 **/
public class UserUtil {

    // 获取当前登录的用户
    public static SysUser getCurrentUser(){
        return (SysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // 获取当前登录的用户名
    public static String getUsername(){
        return getCurrentUser().getUsername();
    }
}
