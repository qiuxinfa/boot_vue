package com.qxf.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qxf.config.SecurityProperties;
import com.qxf.config.TokenProvider;
import com.qxf.entity.SysLoginLog;
import com.qxf.entity.SysPermission;
import com.qxf.entity.SysRole;
import com.qxf.entity.SysUser;
import com.qxf.service.SysLoginLogService;
import com.qxf.service.SysPermissionService;
import com.qxf.service.SysRoleService;
import com.qxf.service.SysUserService;
import com.qxf.util.EnumCode;
import com.qxf.util.IPUtil;
import com.qxf.util.RedisUtil;
import com.qxf.util.ResultUtil;
import com.wf.captcha.ArithmeticCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/8/3 21:01
 **/
@RestController
@RequestMapping("auth")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private RedisUtil redisUtil;

    //用户登录
    @PostMapping("/login")
    public ResultUtil login(@RequestBody SysUser user, HttpServletRequest request,
                            HttpServletResponse response) throws JsonProcessingException {
        String uuid = user.getUuid();
        String code = user.getCode();
        String result = redisUtil.get(uuid);
        if (result == null){
            return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"验证码已过期");
        }
        if (!result.equals(code)){
            return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"验证码不正确");
        }


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = null;
        //认证
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManagerBuilder.getObject().authenticate(token);
        }catch (DisabledException e){
            System.out.println("账号被禁止登录。。。。"+e.getMessage());
            return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"账号已被禁止登录，请联系管理员！");
        }catch (Exception e){
            System.out.println("其他认证异常。。"+e.getMessage());
            return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"用户名或密码错误！");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String jwtToken = tokenProvider.createToken(authentication);
        // 返回 token 与 用户信息
        Map<String,Object> authInfo = new HashMap<>(3);
        authInfo.put("token", securityProperties.getTokenStartWith() + jwtToken);
        authInfo.put("tokenExpiredTime",new Date().getTime() + securityProperties.getTokenValidityInSeconds());
        authInfo.put("user",authentication.getPrincipal());
        logger.info(user.getUsername()+" ：登录成功");
        // 记录登录日志
        SysUser currentUser = (SysUser)authentication.getPrincipal();
        //缓存到redis
        redisUtil.set(currentUser.getUsername(), JSON.toJSONString(currentUser));
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setId(UUID.randomUUID().toString().replace("-",""));
        loginLog.setLoginTime(new Date());
        loginLog.setUserId(currentUser.getId());
        loginLog.setIp(IPUtil.getIPAddress(request));
        sysLoginLogService.insert(loginLog);
        return new ResultUtil(EnumCode.OK.getValue(),"登录成功！",authInfo);
    }

    //查询权限
    @GetMapping("/menu")
    public ResultUtil getMenuList(String userId){
        if (userId == null || "".equals(userId)){
            userId = "1";
        }
        //根据用户id，查询菜单列表
        List<SysPermission> sysPermissions = sysPermissionService.getPermissionListByUserId(userId);
        // 菜单树形化
        List<SysPermission> menuList = sysPermissionService.selectMenuTree(sysPermissions);
        return new ResultUtil(EnumCode.OK.getValue(),menuList);
    }

    @PostMapping("/logout")
    public ResultUtil logout(){
//        SecurityContextHolder.clearContext();
        return new ResultUtil(EnumCode.OK.getValue(),"已退出登录！");
    }

    // 验证码
    @GetMapping(value = "/code")
    public ResultUtil getCode(){
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        // 每次产生验证码的唯一标志
        String uuid = UUID.randomUUID().toString().replace("-","");
        // 保存，过期时间2分钟
        redisUtil.set(uuid, result, 2L, TimeUnit.MINUTES);
        // 验证码信息
        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
            put("imgUrl", captcha.toBase64());
            put("uuid", uuid);
        }};
        return new ResultUtil(EnumCode.OK.getValue(),imgResult);
    }

}
