package com.qxf.service.impl;

import com.qxf.dao.SysUserDao;
import com.qxf.entity.SysUser;
import com.qxf.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    /**
     * 通过用户名查询用户信息
     *
     * @param s 用户名
     * @return 实例对象
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = sysUserDao.getUserByUsername(s);
        return user;
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
    public SysUser insert(SysUser sysUser) {
        this.sysUserDao.insert(sysUser);
        return sysUser;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserDao.update(sysUser);
        return this.queryById(sysUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysUserDao.deleteById(id) > 0;
    }

}