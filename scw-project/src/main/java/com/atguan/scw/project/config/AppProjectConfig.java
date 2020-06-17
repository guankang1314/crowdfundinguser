package com.atguan.scw.project.config;


import com.atguan.scw.project.component.OssTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class AppProjectConfig {

    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OssTemplate ossTemplate() {
        return new OssTemplate();
    }
}
