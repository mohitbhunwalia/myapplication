package com.mettl.disha.aadhar.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.mettl.disha.aadhar")
public class FeignConfiguration {

}
