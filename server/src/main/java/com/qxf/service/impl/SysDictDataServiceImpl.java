package com.qxf.service.impl;

import com.qxf.dao.SysDictDataDao;
import com.qxf.entity.SysDictData;
import com.qxf.service.SysDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典数据表(SysDictData)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 21:30:44
 */
@Service("sysDictDataService")
public class SysDictDataServiceImpl implements SysDictDataService {
    @Resource
    private SysDictDataDao sysDictDataDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDictData queryById(String id) {
        return this.sysDictDataDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDictData> queryAllByLimit(int offset, int limit) {
        return this.sysDictDataDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysDictData 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictData insert(SysDictData sysDictData) {
        this.sysDictDataDao.insert(sysDictData);
        return sysDictData;
    }

    /**
     * 修改数据
     *
     * @param sysDictData 实例对象
     * @return 实例对象
     */
    @Override
    public SysDictData update(SysDictData sysDictData) {
        this.sysDictDataDao.update(sysDictData);
        return this.queryById(sysDictData.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysDictDataDao.deleteById(id) > 0;
    }
}