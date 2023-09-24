package com.iThelp.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PhoneBookWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneBookWebApplication.class, args);
	}
	//this is for testing purpose
}
