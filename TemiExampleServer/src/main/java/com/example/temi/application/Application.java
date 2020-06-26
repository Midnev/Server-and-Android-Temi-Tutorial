package com.example.temi.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Example Created By Sungmin Park
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.temi"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
