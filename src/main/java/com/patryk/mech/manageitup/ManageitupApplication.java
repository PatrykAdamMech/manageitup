package com.patryk.mech.manageitup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }) // disable DB connection for now
public class ManageitupApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageitupApplication.class, args);
	}

}
