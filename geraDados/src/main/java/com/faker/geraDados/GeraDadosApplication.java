package com.faker.geraDados;

import com.faker.geraDados.service.DataGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeraDadosApplication implements CommandLineRunner {

	@Autowired
	private DataGeneratorService dataGeneratorService;

	public static void main(String[] args) {
		SpringApplication.run(GeraDadosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dataGeneratorService.generateData();
	}
}
