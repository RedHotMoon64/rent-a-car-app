package org.example.rent.a.car.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class RentACarApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentACarApplication.class, args);
    }
}
