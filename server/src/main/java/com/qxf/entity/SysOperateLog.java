package com.qxf.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志(SysOperateLog)实体类
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
public class SysOperateLog implements Serializable {
    private static final long serialVersionUID = 217852390463541876L;
    
    private String id;
    /**
    * 请求url
    */
    private String requestUrl;
    /**
    * 地址
    */
    private String remoteAddr;
    /**
    * 请求参数
    */
    private String params;
    /**
    * 请求方法
    */
    private String method;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 用户id
    */
    private String userId;
    /**
    * 请求是否成功，1成功，0失败
    */
    private Integer isSuccess;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

}