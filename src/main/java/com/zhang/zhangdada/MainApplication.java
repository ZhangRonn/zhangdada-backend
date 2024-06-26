package com.zhang.zhangdada;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主类（项目启动入口）
 *
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.zhang.zhangdada.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        System.out.println("启动成功啦啦！！！！！！！！！");
        System.out.println("静姐！！！！！！！！！！！！！！");

    }

}
