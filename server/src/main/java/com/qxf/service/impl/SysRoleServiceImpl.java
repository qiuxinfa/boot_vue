package com.qxf.service.impl;

import com.qxf.dao.SysRoleDao;
import com.qxf.dao.SysRolePermissionDao;
import com.qxf.entity.SysRole;
import com.qxf.entity.SysRolePermission;
import com.qxf.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Resource
    private SysRolePermissionDao sysRolePermissionDao;

    @Override
    public List<SysRole> queryAll(SysRole sysRole) {
        return sysRoleDao.queryAll(sysRole);
    }

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
    public int insert(SysRole sysRole) {
        sysRole.setId(UUID.randomUUID().toString().replace("-",""));
        sysRole.setCreateTime(new Date());
        // 角色权限关联表
        String permissionIds = sysRole.getPermissionIds();
        if (!StringUtils.isEmpty(permissionIds)){
            // 关联角色和权限
            linkRolePermission(permissionIds,sysRole.getId());
        }
        return sysRoleDao.insert(sysRole);
    }

    // 关联角色和权限
    private void linkRolePermission(String permissionIds,String roleId){
        String[] idArr = permissionIds.split(",");
        for (String permissionId : idArr){
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setId(UUID.randomUUID().toString().replace("-",""));
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermissionDao.insert(sysRolePermission);
        }
    }
    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        // 角色权限关联表
        String permissionIds = sysRole.getPermissionIds();
        if (!StringUtils.isEmpty(permissionIds)){
            // 删除旧的权限
            sysRolePermissionDao.deleteByRoleId(sysRole.getId());
            // 插入新的权限
            linkRolePermission(permissionIds,sysRole.getId());
        }
        return sysRoleDao.update(sysRole);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(String id) {
        return sysRoleDao.deleteById(id);
    }
}