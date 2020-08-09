package com.qxf.dao;

import com.qxf.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限(SysPermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 20:41:03
 */
public interface SysPermissionDao {
    // 获取所有的权限
    List<SysPermission> getAllPermissionList(@Param("list") List<Integer> typeList);
    // 根据用户id，查询权限列表
    List<SysPermission> getPermissionListByUserId(@Param("userId") String userId);

    // 根据角色id集合，查询权限列表
    List<SysPermission> getPermissionListByRoleIds(@Param("list") List<String> roleIds);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermission queryById(String id);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysPermission 实例对象
     * @return 对象列表
     */
    List<SysPermission> queryAll(SysPermission sysPermission);

    /**
     * 新增数据
     *
     * @param sysPermission 实例对象
     * @return 影响行数
     */
    int insert(SysPermission sysPermission);

    /**
     * 修改数据
     *
     * @param sysPermission 实例对象
     * @return 影响行数
     */
    int update(SysPermission sysPermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}