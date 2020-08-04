package com.qxf.service.impl;

import com.qxf.dao.SysRoleDao;
import com.qxf.entity.SysRole;
import com.qxf.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 20:41:05
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> getRolesByUserId(String userId) {
        return sysRoleDao.getRolesByUserId(userId);
    }

    @Override
    public List<SysRole> getRoleList() {
        return sysRoleDao.getRoleList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRole queryById(String id) {
        return this.sysRoleDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysRole insert(SysRole sysRole) {
        this.sysRoleDao.insert(sysRole);
        return sysRole;
    }

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysRole update(SysRole sysRole) {
        this.sysRoleDao.update(sysRole);
        return this.queryById(sysRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysRoleDao.deleteById(id) > 0;
    }
}