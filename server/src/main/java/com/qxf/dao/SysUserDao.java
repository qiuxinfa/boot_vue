package com.qxf.dao;

import com.qxf.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
public interface SysUserDao {
    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 实例对象
     */
    SysUser getUserByUsername(String username);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(String id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param
     * @return 对象列表
     */
    List<SysUser> queryAll(@Param("username") String username);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(@Param("id") String id);

}