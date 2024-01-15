package com.bookmanagement.bookmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class BookmanagementApplication {

	public static void main(String[] args) {
		Logger logger= LoggerFactory.getLogger(BookmanagementApplication.class);
		logger.debug("Main application is Executed");
		SpringApplication.run(BookmanagementApplication.class, args);
	}

}
