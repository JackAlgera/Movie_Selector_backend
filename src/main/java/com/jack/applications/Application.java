package com.jack.applications;

import com.jack.applications.handlers.RoomHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		RoomHandler roomHandler = RoomHandler.getInstance();

		SpringApplication.run(Application.class, args);
	}

}
