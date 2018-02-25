package com.carsoline.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://db4free.net:3307/jdevson93?useSSL=false&serverTimezone=UTC");//change url
        dataSource.setUsername("jdevson93");//change userid
        dataSource.setPassword("jdevson");//change pwd

        return dataSource;
    }

}