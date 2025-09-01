package br.edu.ifba.conectairece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiConectaIreceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiConectaIreceApplication.class, args);
    }

}
