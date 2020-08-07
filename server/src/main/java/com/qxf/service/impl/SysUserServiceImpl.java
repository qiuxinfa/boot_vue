package com.qxf.service.impl;

import com.qxf.dao.SysPermissionDao;
import com.qxf.dao.SysRoleDao;
import com.qxf.dao.SysUserDao;
import com.qxf.entity.SysPermission;
import com.qxf.entity.SysRole;
import com.qxf.entity.SysUser;
import com.qxf.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService,UserDetailsService {
    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysPermissionDao sysPermissionDao;

    /**
     * 通过用户名查询用户信息
     *
     * @param s 用户名
     * @return 实例对象
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = sysUserDao.getUserByUsername(s);
        if (user != null){
            //设置角色
            List<SysRole> roles = sysRoleDao.getRolesByUserId(user.getId());
            user.setRoleList(roles);

            // 设置权限，实现基于url的访问控制
            List<SysPermission> permissions = new ArrayList<>();
            if (roles != null && roles.size() > 0){
                List<String> roleIds = new ArrayList<>(roles.size());
                for (SysRole role : roles){
                    roleIds.add(role.getId());
                }
                permissions = sysPermissionDao.getPermissionListByRoleIds(roleIds);
            }
            user.setPermissionList(permissions);
        }
        return user;
    }

    @Override
    public List<SysUser> queryAll(String username) {
        return sysUserDao.queryAll(username);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(String id) {
        return this.sysUserDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(SysUser sysUser) {
        return sysUserDao.insert(sysUser);
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SysUser sysUser) {
        return sysUserDao.update(sysUser);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(String id) {
        return this.sysUserDao.deleteById(id);
    }

}