package com.otis.play;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaSpringBootPlayApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaSpringBootPlayApplication.class, args);
	}
}
