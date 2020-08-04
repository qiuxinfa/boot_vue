package com.qxf.service.impl;

import com.qxf.dao.SysOperateLogDao;
import com.qxf.entity.SysOperateLog;
import com.qxf.service.SysOperateLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志(SysOperateLog)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
@Service("sysOperateLogService")
public class SysOperateLogServiceImpl implements SysOperateLogService {
    @Resource
    private SysOperateLogDao sysOperateLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysOperateLog queryById(String id) {
        return this.sysOperateLogDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysOperateLog> queryAllByLimit(int offset, int limit) {
        return this.sysOperateLogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysOperateLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysOperateLog insert(SysOperateLog sysOperateLog) {
        this.sysOperateLogDao.insert(sysOperateLog);
        return sysOperateLog;
    }

    /**
     * 修改数据
     *
     * @param sysOperateLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysOperateLog update(SysOperateLog sysOperateLog) {
        this.sysOperateLogDao.update(sysOperateLog);
        return this.queryById(sysOperateLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysOperateLogDao.deleteById(id) > 0;
    }
}