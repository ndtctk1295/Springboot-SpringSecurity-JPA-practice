//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//    @Value("${spring.datasource.mysql.url}")
//    private String mysqlUrl;
//
//    @Value("${spring.datasource.mysql.username}")
//    private String mysqlUsername;
//
//    @Value("${spring.datasource.mysql.password}")
//    private String mysqlPassword;
//
////    @Bean
////    public DataSource mysqlDataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
////        dataSource.setUrl(mysqlUrl);
////        dataSource.setUsername(mysqlUsername);
////        dataSource.setPassword(mysqlPassword);
////        return dataSource;
////    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.mysql")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.h2")
//    public DataSource h2DataSource() {
//        return DataSourceBuilder.create().build();
//    }
//}