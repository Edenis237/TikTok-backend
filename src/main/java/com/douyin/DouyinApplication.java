package com.douyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient//开启服务注册与发现
@MapperScan("com.douyin.mapper")
@ServletComponentScan("cn.dev33.satoken")
public class DouyinApplication {
    public static void main(String[] args) {

        SpringApplication.run(DouyinApplication.class, args);
    }
}
