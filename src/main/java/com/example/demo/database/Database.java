package com.example.demo.database;

import com.example.demo.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
//    init fake data when server startup
//        logger to printout data, function of relevant database (replace the printoutln method)
private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    public CommandLineRunner initDatabase (ProductRepository productRepository){

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product productA = new Product( "MacBook Pro 15", 2029, 2400.0, "");
//                Product productB = new Product( "iPad air", 2021, 599.0, "");
//                logger.info("insert data: " + productRepository.save(productA));
//                logger.info("insert data: " + productRepository.save(productB));
            }
        };

    }
}
