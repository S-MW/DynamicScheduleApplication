package com.example.dynamicschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DynamicScheduleApplication {

    public static void main(String[] args) {

        SpringApplication.run(DynamicScheduleApplication.class, args);

    }

}
