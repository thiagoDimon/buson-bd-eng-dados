package com.faker.geraDados;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeraDadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeraDadosApplication.class, args);
                
                Faker faker = new Faker();
	}

}
