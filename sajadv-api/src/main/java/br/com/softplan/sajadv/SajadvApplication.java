package br.com.softplan.sajadv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SajadvApplication {

	public static void main(String[] args) {
		SpringApplication.run(SajadvApplication.class, args);
	}

}
