package com.qxf.entity;

import java.io.Serializable;

/**
 * 角色-权限(SysRolePermission)实体类
 *
 * @author makejava
 * @since 2020-08-03 20:41:05
 */
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 452667797051160216L;
    /**
    * 主键id
    */
    private String id;
    /**
    * 角色id
    */
    private String roleId;
    /**
    * 权限id
    */
    private String permissionId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

}