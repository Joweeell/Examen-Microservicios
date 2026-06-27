package com.example.Micro_Ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroVentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroVentasApplication.class, args);
	}

}
