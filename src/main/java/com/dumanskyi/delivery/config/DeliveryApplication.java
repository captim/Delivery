package com.dumanskyi.delivery.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.dumanskyi.delivery")
@ComponentScan({"com.dumanskyi.delivery"})
@EnableJpaRepositories(basePackages = "com.dumanskyi.delivery.persistence")
@EntityScan("com.dumanskyi.delivery.entities")
public class DeliveryApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DeliveryApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

}
