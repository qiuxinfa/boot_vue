package com.qxf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtil
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/8/10 22:29
 **/
@Component
public class RedisUtil {
    // 默认过期时间，1天
    private Long defaultExpireTime = 24*60L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value,defaultExpireTime, TimeUnit.MINUTES);
    }

    public void set(String key, String value, Long expireTime){
        stringRedisTemplate.opsForValue().set(key,value,expireTime, TimeUnit.MINUTES);
    }

    public void set(String key, String value, Long expireTime, TimeUnit timeUnit){
        stringRedisTemplate.opsForValue().set(key,value,expireTime,timeUnit);
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }


}
