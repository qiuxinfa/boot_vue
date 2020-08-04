package com.qxf.dao;

import com.qxf.entity.SysLoginLog;

import java.util.List;

/**
 * 登录日志(SysLoginLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
public interface SysLoginLogDao {
    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysLoginLog 实例对象
     * @return 对象列表
     */
    List<SysLoginLog> queryAll(SysLoginLog sysLoginLog);

    /**
     * 新增数据
     *
     * @param sysLoginLog 实例对象
     * @return 影响行数
     */
    int insert(SysLoginLog sysLoginLog);
}