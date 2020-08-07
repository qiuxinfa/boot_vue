package com.qxf.dao;

import com.qxf.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 20:41:05
 */
public interface SysRoleDao {

    // 根据用户id，查询角色列表
    List<SysRole> getRolesByUserId(@Param("userId") String userId);

    // 获取角色下拉列表
    List<SysRole> getRoleList();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRole queryById(String id);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysRole 实例对象
     * @return 对象列表
     */
    List<SysRole> queryAll(SysRole sysRole);

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int insert(SysRole sysRole);

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int update(SysRole sysRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}