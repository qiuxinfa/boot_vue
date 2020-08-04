package com.qxf.dao;

import com.qxf.entity.SysDictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型表(SysDictType)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
public interface SysDictTypeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDictType queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDictType> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysDictType 实例对象
     * @return 对象列表
     */
    List<SysDictType> queryAll(SysDictType sysDictType);

    /**
     * 新增数据
     *
     * @param sysDictType 实例对象
     * @return 影响行数
     */
    int insert(SysDictType sysDictType);

    /**
     * 修改数据
     *
     * @param sysDictType 实例对象
     * @return 影响行数
     */
    int update(SysDictType sysDictType);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}