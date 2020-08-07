package com.qxf.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 权限(SysPermission)实体类
 *
 * @author makejava
 * @since 2020-08-03 20:41:02
 */
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 851073053078429186L;
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
    * 权限名称
    */
    private String name;
    /**
    * 权限路径
    */
    private String url;
    /**
    * 前端组件路径，从views目录开始算
    */
    private String component;
    /**
    * icon图标名称
    */
    private String icon;
    /**
    * 权限类型，0-目录，1菜单，2-按钮
    */
    private Integer type;
    /**
    * 父权限id
    */
    private String parentId;
    /**
    * 排序号
    */
    private Integer sort;

    private List<SysPermission> children = new ArrayList<>();

    public List<SysPermission> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermission> children) {
        this.children = children;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}