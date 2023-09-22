package com.kob.matchingsystem;

import com.kob.matchingsystem.service.Impl.MatchingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        //启动匹配线程
        MatchingServiceImpl.matchingPool.start();

        SpringApplication.run(MatchingSystemApplication.class,args);
    }
}