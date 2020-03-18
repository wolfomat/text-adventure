package com.wolfo;


import javax.persistence.Entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan({"com.wolfo.quest", "com.wolfo.database"})
@EntityScan(basePackages = {"com.wolfo"})
public class Init {

	public static void main(String[] args) {
		SpringApplication.run(Init.class, args);
	}

}

