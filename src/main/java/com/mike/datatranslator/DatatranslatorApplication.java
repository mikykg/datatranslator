package com.mike.datatranslator;

import com.mike.datatranslator.service.DataTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DatatranslatorApplication implements CommandLineRunner {

	@Autowired
	DataTranslator dataTranslator;

	public static void main(String[] args) {
		SpringApplication.run(DatatranslatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//dataTranslator.translate();
	}
}
