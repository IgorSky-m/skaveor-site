package com.skachko.shop.account.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class MsgService {
    public static void main(String[] args) {
        SpringApplication.run(MsgService.class, args);
    }
}