package com.qxf.controller;

import com.qxf.entity.SysRolePermission;
import com.qxf.service.SysRolePermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色-权限(SysRolePermission)表控制层
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
@RestController
@RequestMapping("sysRolePermission")
public class SysRolePermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysRolePermission selectOne(String id) {
        return this.sysRolePermissionService.queryById(id);
    }

}