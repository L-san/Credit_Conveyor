package ru.lsan.conveyor;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Log4j2
@SpringBootApplication
@EnableSwagger2
public class ConveyorApplication {

    public static void main(String[] args) {
        log.info("conveyor service started");
        SpringApplication.run(ConveyorApplication.class, args);
    }

}
