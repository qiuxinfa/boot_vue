package com.qxf.service.impl;

import com.qxf.dao.SysPermissionDao;
import com.qxf.entity.SysPermission;
import com.qxf.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 权限(SysPermission)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 20:41:04
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionDao sysPermissionDao;

    @Override
    public List<SysPermission> selectMenuTreeByUserId(String userId) {
        // 查找出菜单或目录，再封装成树形结构
        List<SysPermission> allMenu = sysPermissionDao.getPermissionListByUserId(userId);
        // 根目录
        List<SysPermission> rootMenu = new LinkedList<>();
//        List<SysPermission> common = new LinkedList<>();
        if (allMenu != null && allMenu.size() > 0){
            for (SysPermission m : allMenu){
                // 找出根目录
                if ("0".equals(m.getParentId())){
                    rootMenu.add(m);
                }
            }
            // 为根菜单设置子菜单或子目录，getClild是递归调用的
            for (SysPermission root : rootMenu) {
                // 获取根节点下的所有子节点 使用getChild方法
                List<SysPermission> childList = getChild(root.getId(), allMenu);
                // 给根节点设置子节点
                root.setChildren(childList);
            }
        }

        return rootMenu;
    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<SysPermission> getChild(String id, List<SysPermission> allMenu) {
        //子菜单
        List<SysPermission> childList = new ArrayList<>();
        for (SysPermission nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getParentId().equals(id)) {
                childList.add(nav);
            }
        }
        //递归
        for (SysPermission nav : childList) {
            nav.setChildren(getChild(nav.getId(), allMenu));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList == null || childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }

    @Override
    public List<SysPermission> getPermissionListByUserId(String userId) {
        return sysPermissionDao.getPermissionListByUserId(userId);
    }

    @Override
    public List<SysPermission> getPermissionListByRoleIds(List<String> roleIds) {
        return sysPermissionDao.getPermissionListByRoleIds(roleIds);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysPermission queryById(String id) {
        return this.sysPermissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysPermission> queryAllByLimit(int offset, int limit) {
        return this.sysPermissionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysPermission 实例对象
     * @return 实例对象
     */
    @Override
    public SysPermission insert(SysPermission sysPermission) {
        this.sysPermissionDao.insert(sysPermission);
        return sysPermission;
    }

    /**
     * 修改数据
     *
     * @param sysPermission 实例对象
     * @return 实例对象
     */
    @Override
    public SysPermission update(SysPermission sysPermission) {
        this.sysPermissionDao.update(sysPermission);
        return this.queryById(sysPermission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysPermissionDao.deleteById(id) > 0;
    }
}