package com.cream.warriorLegends;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@MapperScan("com.cream.warriorLegends.mapper")
@SpringBootApplication
public class WarriorLegendsServerApp {

	public static void main(String[] args) {
		SpringApplication.run(WarriorLegendsServerApp.class, args);
	}
}

@Slf4j
@Component
class AppStartupRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("SpringApplication started...");
	}
}