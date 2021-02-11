package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 * 
 */
@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    /**
     * Initializes the price so that we can send this to Vehicle API.
     * @param repository where the price information persists.
     * @return the prices  to add to the related repository
     */
    @Bean
    CommandLineRunner initDatabase(PriceRepository repository){
        return args -> {
            for(long i=1; i<21; i++) {
                repository.save(new Price("USD",randomPrice(),i));
            }
        };
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}
