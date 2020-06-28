package br.com.portfolio.lsilva.sajadv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@RefreshScope
@EnableCaching
@EnableEurekaClient
@SpringBootApplication
public class SajadvPersonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SajadvPersonsApplication.class, args);
	}

}
