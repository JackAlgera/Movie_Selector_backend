package com.jack.applications;

import com.jack.applications.web.database.DatabaseHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		DatabaseHandler.getInstance();
		//SpringApplication.run(Application.class, args);
	}

}
