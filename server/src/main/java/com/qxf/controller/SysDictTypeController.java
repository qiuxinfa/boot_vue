package com.qxf.controller;

import com.qxf.entity.SysDictType;
import com.qxf.service.SysDictTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 字典类型表(SysDictType)表控制层
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
@RestController
@RequestMapping("sysDictType")
public class SysDictTypeController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysDictType selectOne(String id) {
        return this.sysDictTypeService.queryById(id);
    }

}