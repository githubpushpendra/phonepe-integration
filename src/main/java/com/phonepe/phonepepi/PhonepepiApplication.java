package com.phonepe.phonepepi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan
public class PhonepepiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhonepepiApplication.class, args);
	}

}
