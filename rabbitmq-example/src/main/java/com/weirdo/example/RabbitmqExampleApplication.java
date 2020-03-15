package com.weirdo.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.weirdo.example.mapper")
@SpringBootApplication
public class RabbitmqExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqExampleApplication.class, args);
    }

}
