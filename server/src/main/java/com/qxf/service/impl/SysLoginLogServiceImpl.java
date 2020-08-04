package com.qxf.service.impl;

import com.qxf.dao.SysLoginLogDao;
import com.qxf.entity.SysLoginLog;
import com.qxf.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录日志(SysLoginLog)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
@Service("sysLoginLogService")
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Resource
    private SysLoginLogDao sysLoginLogDao;

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysLoginLog 实例对象
     * @return 对象列表
     */
    @Override
    public List<SysLoginLog> queryAll(SysLoginLog sysLoginLog) {
        return sysLoginLogDao.queryAll(sysLoginLog);
    }

    /**
     * 新增数据
     *
     * @param sysLoginLog 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(SysLoginLog sysLoginLog) {
        return sysLoginLogDao.insert(sysLoginLog);
    }

}