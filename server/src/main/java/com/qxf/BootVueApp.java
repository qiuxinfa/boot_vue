package com.qxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName BootVueApp
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/8/2 22:57
 **/
@SpringBootApplication
@MapperScan("com.qxf.dao")
public class BootVueApp {
    public static void main(String[] args) {
        SpringApplication.run(BootVueApp.class,args);
    }
}
