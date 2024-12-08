package com.web.seo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = {"com.web.seo"})
public class SeoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeoApplication.class, args);
	}

}
