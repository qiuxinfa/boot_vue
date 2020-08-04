package com.qxf.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志(SysLoginLog)实体类
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
public class SysLoginLog implements Serializable {
    private static final long serialVersionUID = -18101402076974347L;
    /**
    * 主键id
    */
    private String id;
    /**
    * 用户id
    */
    private String userId;
    /**
    * 登录时间
    */
    private Date loginTime;
    /**
    * 登录ip
    */
    private String ip;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}