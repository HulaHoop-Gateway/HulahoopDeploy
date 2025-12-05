package com.hulahoop.redback;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hulahoop.redback")
@SpringBootApplication
public class RedBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedBackApplication.class, args);
    }

}
