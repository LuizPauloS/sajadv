package br.com.softplan.sajadveurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SajadvEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SajadvEurekaServerApplication.class, args);
	}

}
