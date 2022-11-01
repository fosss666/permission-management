package com.fosss.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.fosss.system.mapper")
//配置扫描
@ComponentScan(basePackages = "com.fosss")
public class SystemAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemAuthApplication.class, args);
    }
}
