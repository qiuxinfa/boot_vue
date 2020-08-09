package com.qxf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qxf.entity.SysRole;
import com.qxf.service.SysRoleService;
import com.qxf.util.EnumCode;
import com.qxf.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 角色(SysRole)表控制层
 *
 * @author makejava
 * @since 2020-08-03 20:41:05
 */
@RestController
@RequestMapping("role")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("/list")
    public Object getListByPage(Integer startPage,Integer pageSize,SysRole sysRole){
        PageHelper.startPage(startPage,pageSize);
        List<SysRole> list = sysRoleService.queryAll(sysRole);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return new ResultUtil(EnumCode.OK.getValue(),"请求成功",list,pageInfo.getTotal());
    }

    @PostMapping("/add")
    public ResultUtil addUser(@RequestBody SysRole sysRole){
        String msg = "新增失败！";
        if (sysRole != null){
            Integer cnt = sysRoleService.insert(sysRole);
            if (cnt > 0){
                msg = "新增成功！";
            }
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    @PostMapping("/update")
    public ResultUtil updateUser(@RequestBody SysRole sysRole){
        String msg = "修改失败！";
        Integer cnt = sysRoleService.update(sysRole);
        if (cnt > 0){
            msg = "修改成功！";
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    @PostMapping("/delete")
    public ResultUtil deleteUser(String id){
        String msg = "0";
        Integer cnt = sysRoleService.deleteById(id);
        if (cnt > 0){
            msg = "1";
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    // 获取角色下拉列表
    @GetMapping("/getRoleList")
    public ResultUtil getRoleList(){
        List<SysRole> roleList = sysRoleService.getRoleList();
        return new ResultUtil(EnumCode.OK.getValue(),roleList);
    }

}