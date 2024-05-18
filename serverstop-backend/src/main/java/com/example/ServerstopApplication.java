package com.example;

import org.hibernate.cfg.Environment;
import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.security.SecurityProperty;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperty.class})
@ConfigurationProperties
public class ServerstopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerstopApplication.class, args);
	}

}
