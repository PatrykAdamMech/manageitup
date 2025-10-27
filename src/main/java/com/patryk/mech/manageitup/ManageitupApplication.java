package com.patryk.mech.manageitup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class }) // disable DB connection for now
//@ComponentScans(@ComponentScan("/repositories"))
@ComponentScan(basePackages = {
		"com.patryk.mech.manageitup",
		"com.patryk.mech.manageitup.repositories",
		"com.patryk.mech.manageitup.config",
		"com.patryk.mech.manageitup.secirity"})
public class ManageitupApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageitupApplication.class, args);
	}

}
