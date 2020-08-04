package com.qxf.dao;

import com.qxf.entity.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据表(SysDictData)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 21:30:42
 */
public interface SysDictDataDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDictData queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDictData> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysDictData 实例对象
     * @return 对象列表
     */
    List<SysDictData> queryAll(SysDictData sysDictData);

    /**
     * 新增数据
     *
     * @param sysDictData 实例对象
     * @return 影响行数
     */
    int insert(SysDictData sysDictData);

    /**
     * 修改数据
     *
     * @param sysDictData 实例对象
     * @return 影响行数
     */
    int update(SysDictData sysDictData);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}