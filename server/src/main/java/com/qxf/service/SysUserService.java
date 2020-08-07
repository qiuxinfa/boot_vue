package com.qxf.service;

import com.qxf.entity.SysUser;

import java.util.List;

/**
 * 用户(SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
public interface SysUserService {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param
     * @return 对象列表
     */
    List<SysUser> queryAll(String username);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(String id);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    int insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    int deleteById(String id);

}