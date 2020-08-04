package com.qxf.controller;

import com.qxf.entity.SysLoginLog;
import com.qxf.service.SysLoginLogService;
import com.qxf.util.EnumCode;
import com.qxf.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录日志(SysLoginLog)表控制层
 *
 * @author makejava
 * @since 2020-08-03 21:30:45
 */
@RestController
@RequestMapping("sysLoginLog")
public class SysLoginLogController {
    /**
     * 服务对象
     */
    @Resource
    private SysLoginLogService sysLoginLogService;

    public ResultUtil getLoginLogList(SysLoginLog sysLoginLog){
        return new ResultUtil(EnumCode.OK.getValue(),"");
    }

}