package com.cream.warriorLegends;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cream.warriorLegends.mapper")
@SpringBootApplication
public class WarriorLegendsServerApp {

	public static void main(String[] args) {
		SpringApplication.run(WarriorLegendsServerApp.class, args);
	}
}
