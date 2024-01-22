//package com.example.demo.config;
//
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class EntityManagerFactoryConfig {
//
//    @Value("${spring.datasource.mysql.url}")
//    private String mysqlUrl;
//
//    @Value("${spring.datasource.mysql.username}")
//    private String mysqlUsername;
//
//    @Value("${spring.datasource.mysql.password}")
//    private String mysqlPassword;
//
//    @Bean
//    public DataSource sqlDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl(mysqlUrl);
//        dataSource.setUsername(mysqlUsername);
//        dataSource.setPassword(mysqlPassword);
//        return dataSource;
//    }
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(sqlDataSource());
//        em.setPackagesToScan(new String[] { "com.example.demo.models" });
//        // Set JPA properties if necessary
//        return em;
//    }
//
//    @Bean(name = "jpaSharedEM_entityManagerFactory")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
//            EntityManagerFactoryBuilder builder, @Qualifier("mysqlDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.demo.models.mysql") // Your MySQL entities package
//                .persistenceUnit("mysql")
//                .build();
//    }
//
//    @Bean(name = "h2EntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean h2EntityManagerFactory(
//            EntityManagerFactoryBuilder builder, @Qualifier("h2DataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.demo.models") // Your H2 entities package
//                .persistenceUnit("h2")
//                .build();
//    }
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }
//}
