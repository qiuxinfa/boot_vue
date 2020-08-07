package com.qxf.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户(SysUser)实体类
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
public class SysUser implements UserDetails,Serializable {
    private static final long serialVersionUID = 166910488680037707L;
    /**
    * 主键id
    */
    private String id;
    /**
    * 有效标志，1有效，0无效
    */
    private Integer isValid;
    /**
    * 创建者id
    */
    private String createBy;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新者id
    */
    private String updateBy;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 手机号码
    */
    private String phone;
    /**
    * 头像地址
    */
    private String avater;
    /**
    * 性别，1男，2女，3未知
    */
    private Integer sex;

    //角色id
    private String roleIds;
    //角色名称
    private String roleName;

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private List<SysRole> roleList = new ArrayList<>();
    private List<SysPermission> permissionList = new ArrayList<>();

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public List<SysPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        int a = getIsValid() == null ? 0 : 1;
        return a == 1;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // 权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 实现基于url的访问控制
        List<SimpleGrantedAuthority> list = null;
        List<SysPermission> permissionList = getPermissionList();
        if (permissionList !=null && permissionList.size() > 0){
            list = new ArrayList<>(permissionList.size());
            SimpleGrantedAuthority simpleGrantedAuthority = null;
            for (SysPermission p : permissionList){
                simpleGrantedAuthority = new SimpleGrantedAuthority(p.getUrl());
                list.add(simpleGrantedAuthority);
            }
        }
        return list;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

}