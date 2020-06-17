package com.atguan.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.atguan.scw.order.mapper")
@SpringBootApplication
public class ScwOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwOrderApplication.class, args);
    }

}
