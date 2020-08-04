package com.qxf.controller;

import com.qxf.entity.SysOperateLog;
import com.qxf.service.SysOperateLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 操作日志(SysOperateLog)表控制层
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
@RestController
@RequestMapping("sysOperateLog")
public class SysOperateLogController {
    /**
     * 服务对象
     */
    @Resource
    private SysOperateLogService sysOperateLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysOperateLog selectOne(String id) {
        return this.sysOperateLogService.queryById(id);
    }

}