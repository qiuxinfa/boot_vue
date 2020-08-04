package com.qxf.service.impl;

import com.qxf.dao.SysDictTypeDao;
import com.qxf.entity.SysDictType;
import com.qxf.service.SysDictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型表(SysDictType)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
@Service("sysDictTypeService")
public class SysDictTypeServiceImpl implements SysDictTypeService {
    @Resource
    private SysDictTypeDao sysDictTypeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDictType queryById(String id) {
        return this.sysDictTypeDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDictType> queryAllByLimit(int offset, int limit) {
        return this.sysDictTypeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysDictType 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictType insert(SysDictType sysDictType) {
        this.sysDictTypeDao.insert(sysDictType);
        return sysDictType;
    }

    /**
     * 修改数据
     *
     * @param sysDictType 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictType update(SysDictType sysDictType) {
        this.sysDictTypeDao.update(sysDictType);
        return this.queryById(sysDictType.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysDictTypeDao.deleteById(id) > 0;
    }
}