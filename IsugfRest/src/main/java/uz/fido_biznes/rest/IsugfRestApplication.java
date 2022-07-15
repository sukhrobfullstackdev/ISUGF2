package uz.fido_biznes.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IsugfRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsugfRestApplication.class, args);
    }

}
