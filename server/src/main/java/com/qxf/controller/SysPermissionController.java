package com.qxf.controller;

import com.qxf.entity.SysPermission;
import com.qxf.service.SysPermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 权限(SysPermission)表控制层
 *
 * @author makejava
 * @since 2020-08-03 20:41:05
 */
@RestController
@RequestMapping("sysPermission")
public class SysPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysPermission selectOne(String id) {
        return this.sysPermissionService.queryById(id);
    }

}