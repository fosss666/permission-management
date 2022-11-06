package com.fosss.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.fosss.system.mapper")
//配置扫描
@ComponentScan(basePackages = "com.fosss")
//开启事务注解
@EnableTransactionManagement
public class SystemAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemAuthApplication.class, args);
    }
}
