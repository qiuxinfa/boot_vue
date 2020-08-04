package com.qxf.service;

import com.qxf.entity.SysDictType;

import java.util.List;

/**
 * 字典类型表(SysDictType)表服务接口
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
public interface SysDictTypeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDictType queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDictType> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDictType 实例对象
     * @return 实例对象
     */
    SysDictType insert(SysDictType sysDictType);

    /**
     * 修改数据
     *
     * @param sysDictType 实例对象
     * @return 实例对象
     */
    SysDictType update(SysDictType sysDictType);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}