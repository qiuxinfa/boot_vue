package com.qxf.entity;

import java.io.Serializable;

/**
 * 用户-角色(SysUserRole)实体类
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -80982995431389158L;
    /**
    * 主键id
    */
    private String id;
    /**
    * 用户id
    */
    private String userId;
    /**
    * 角色id
    */
    private String roleId;


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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}