package com.beachbody.gymservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class used to trigger the application
 */

@SpringBootApplication
@EnableSwagger2
@EnableWebSecurity
@EnableWebMvc
@ComponentScan(basePackages = {"com.beachbody.gymservice"})
public class GymServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymServiceApplication.class, args);
	}

}
