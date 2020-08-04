package com.qxf.service;

import com.qxf.entity.SysOperateLog;

import java.util.List;

/**
 * 操作日志(SysOperateLog)表服务接口
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
public interface SysOperateLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysOperateLog queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysOperateLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysOperateLog 实例对象
     * @return 实例对象
     */
    SysOperateLog insert(SysOperateLog sysOperateLog);

    /**
     * 修改数据
     *
     * @param sysOperateLog 实例对象
     * @return 实例对象
     */
    SysOperateLog update(SysOperateLog sysOperateLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}