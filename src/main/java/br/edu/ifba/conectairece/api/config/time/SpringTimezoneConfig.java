package br.edu.ifba.conectairece.api.config.time;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * Configures the default timezone for the application.
 *
 * <p>Ensures all dates and times use the São Paulo time zone,
 * which is important for consistent logging and date handling.</p>
 *
 * @author Jorge Roberto
 */
@Configuration
public class SpringTimezoneConfig {


    /**
     * Sets the default timezone to America/Sao_Paulo at application startup.
     */
    @PostConstruct
    public void timezoneConfig () {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

}

