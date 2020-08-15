package com.qxf.aspect;

import com.qxf.dao.SysOperateLogDao;
import com.qxf.entity.SysOperateLog;
import com.qxf.util.HttpContextUtil;
import com.qxf.util.IPUtil;
import com.qxf.util.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName LogAspect
 * @Description 操作日志切面
 * @Author qiuxinfa
 * @Date 2020/5/31 18:23
 **/
@Component
@Aspect
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private SysOperateLogDao sysOperateLogDao;

    //第一个*：表示任意修饰符任意返回值
    //第二个*：表示任意类
    //第三个*：表示任意方法
    //参数列表中的2个点：表示任意参数
    @Pointcut("execution(* com.qxf.controller.*.*(..)) && !execution(* com.qxf.controller.LoginController.*(..))")
    public void myPointcut(){

    }

    @Around("myPointcut()")
    public Object myAround(ProceedingJoinPoint point) {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        String params = "";
        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
        }

        // 获取request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        logger.info("执行请求 {} 耗时 {} 毫秒",request.getRequestURI(),time);
        SysOperateLog sysLog = new SysOperateLog();
        sysLog.setId(UUID.randomUUID().toString().replace("-",""));
        sysLog.setRequestUrl(request.getRequestURI());
        sysLog.setRemoteAddr(IPUtil.getIPAddress(request));
        sysLog.setParams(params);
        sysLog.setMethod(request.getMethod());
        sysLog.setCreateTime(new Date());
        sysLog.setUserId(UserUtil.getCurrentUser().getId());
        sysLog.setIsSuccess(1);
        // 保存操作日志
        sysOperateLogDao.insert(sysLog);

    }

}
