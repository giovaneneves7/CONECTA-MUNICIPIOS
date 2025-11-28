package br.com.cidadesinteligentes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConectaMunicipiosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConectaMunicipiosApplication.class, args);
    }

}
