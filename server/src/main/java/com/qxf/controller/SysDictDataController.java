package com.qxf.controller;

import com.qxf.entity.SysDictData;
import com.qxf.service.SysDictDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 字典数据表(SysDictData)表控制层
 *
 * @author makejava
 * @since 2020-08-03 21:30:44
 */
@RestController
@RequestMapping("sysDictData")
public class SysDictDataController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysDictData selectOne(String id) {
        return this.sysDictDataService.queryById(id);
    }

}