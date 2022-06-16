package ru.lsan.deal;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
public class DealApplication {

	public static void main(String[] args) {
		log.info("application started");
		SpringApplication.run(DealApplication.class, args);
	}

}
