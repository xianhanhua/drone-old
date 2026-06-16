package com.example.simpledrone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.simpledrone.mapper")
public class SimpleDroneApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleDroneApplication.class, args);
    }
}
