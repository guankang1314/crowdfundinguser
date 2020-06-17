package com.atguan.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableHystrix
@EnableFeignClients
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan("com.atguan.scw.project.mapper")
@SpringBootApplication
public class ScwProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwProjectApplication.class, args);
    }

}
