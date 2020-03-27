package com.wolfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ComponentScan(basePackages = "com.wolfo")
@EntityScan({"com.wolfo.quest", "com.wolfo.database"})
public class Exec {

	public static void main(String[] args) {
		SpringApplication.run(Exec.class, args);
	}

}
