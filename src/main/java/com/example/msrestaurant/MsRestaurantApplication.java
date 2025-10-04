package com.example.msrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MsRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRestaurantApplication.class, args);
	}

}
