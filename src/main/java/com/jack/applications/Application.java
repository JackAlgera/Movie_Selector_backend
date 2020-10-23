package com.jack.applications;

import com.jack.applications.database.DatabaseHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		DatabaseHandler.getInstance();
		//SpringApplication.run(Application.class, args);
	}

}
