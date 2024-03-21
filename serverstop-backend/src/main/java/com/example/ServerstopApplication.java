package com.example;

import org.hibernate.cfg.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.security.SecurityProperty;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperty.class})
public class ServerstopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerstopApplication.class, args);
	}

}
