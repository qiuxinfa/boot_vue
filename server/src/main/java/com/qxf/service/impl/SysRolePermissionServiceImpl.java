package com.qxf.service.impl;

import com.qxf.dao.SysRolePermissionDao;
import com.qxf.entity.SysRolePermission;
import com.qxf.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色-权限(SysRolePermission)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    @Resource
    private SysRolePermissionDao sysRolePermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRolePermission queryById(String id) {
        return this.sysRolePermissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRolePermission> queryAllByLimit(int offset, int limit) {
        return this.sysRolePermissionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysRolePermission 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolePermission insert(SysRolePermission sysRolePermission) {
        this.sysRolePermissionDao.insert(sysRolePermission);
        return sysRolePermission;
    }

    /**
     * 修改数据
     *
     * @param sysRolePermission 实例对象
     * @return 实例对象
     */
    @Override
    public SysRolePermission update(SysRolePermission sysRolePermission) {
        this.sysRolePermissionDao.update(sysRolePermission);
        return this.queryById(sysRolePermission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysRolePermissionDao.deleteById(id) > 0;
    }
}