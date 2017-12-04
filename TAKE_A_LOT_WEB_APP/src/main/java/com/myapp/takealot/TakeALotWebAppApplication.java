package com.myapp.takealot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.myapp.takealot")
public class TakeALotWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakeALotWebAppApplication.class, args);
               
	}
}
