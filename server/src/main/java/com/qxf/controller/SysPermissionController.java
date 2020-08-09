package com.qxf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qxf.entity.SysPermission;
import com.qxf.service.SysPermissionService;
import com.qxf.util.EnumCode;
import com.qxf.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限(SysPermission)表控制层
 *
 * @author makejava
 * @since 2020-08-03 20:41:05
 */
@RestController
@RequestMapping("permission")
public class SysPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysPermissionService sysPermissionService;

    @GetMapping("/list")
    public Object getListByPage(Integer startPage,Integer pageSize,SysPermission sysPermission){
        PageHelper.startPage(startPage,pageSize);
        List<SysPermission> list = sysPermissionService.queryAll(sysPermission);
        PageInfo<SysPermission> pageInfo = new PageInfo<>(list);
        return new ResultUtil(EnumCode.OK.getValue(),"请求成功",list,pageInfo.getTotal());
    }

    @PostMapping("/add")
    public ResultUtil addUser(@Valid @RequestBody SysPermission permission){
        String msg = "新增失败！";
        Integer cnt = sysPermissionService.insert(permission);
        if (cnt > 0){
            msg = "新增成功！";
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    @PostMapping("/update")
    public ResultUtil updateUser(@RequestBody SysPermission permission){
        String msg = "修改失败！";
        Integer cnt = sysPermissionService.update(permission);
        if (cnt > 0){
            msg = "修改成功！";
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    // @RequestBody 接收无法删除成功，原因未知
    @PostMapping("/delete")
    public ResultUtil deleteUser(String id){
        String msg = "0";
        int cnt = sysPermissionService.deleteById(id);
        if (cnt > 0){
            msg = "1";
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    // 获取权限下拉树
    @GetMapping("/getAllPermission")
    public ResultUtil getAllPermission(@RequestParam String type){
        List<Integer> typeList = new ArrayList<>();
        typeList.add(0); // 目录
        typeList.add(1); // 菜单
        if ("1".equals(type)){
            typeList.add(2); // 功能按钮
        }
        // 获取所有权限
        List<SysPermission> permissionList = sysPermissionService.getAllPermissionList(typeList);
        // 权限树形化
        List<SysPermission> sysPermissions = sysPermissionService.selectMenuTree(permissionList);
        return new ResultUtil(EnumCode.OK.getValue(),sysPermissions);
    }

}