package com.fosss.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fosss.system.mapper")
public class SystemAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemAuthApplication.class, args);
    }
}
