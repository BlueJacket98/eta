package com.capstone.eta;

import com.capstone.eta.util.spring.SpringUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringUtil.class)
public class EtaApplication {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		SpringApplication.run(EtaApplication.class, args);
	}

}
