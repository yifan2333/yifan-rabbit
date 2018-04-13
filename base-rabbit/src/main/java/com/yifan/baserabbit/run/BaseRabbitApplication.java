package com.yifan.baserabbit.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.yifan.baserabbit")
public class BaseRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseRabbitApplication.class, args);
	}
}
