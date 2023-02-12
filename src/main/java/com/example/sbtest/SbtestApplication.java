package com.example.sbtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SbtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbtestApplication.class, args);
	}

}
