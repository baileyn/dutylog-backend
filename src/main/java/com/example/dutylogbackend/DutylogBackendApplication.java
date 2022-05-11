package com.example.dutylogbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DutylogBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(DutylogBackendApplication.class, args);
	}
}
