package org.olympos;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * HadesBootstrapApplication.
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableDubbo
public class HadesBootstrapApplication {

    /**
     * Main Entrance.
     *
     * @param args startup arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run( HadesBootstrapApplication.class, args);
    }
}
