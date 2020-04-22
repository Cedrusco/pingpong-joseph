package com.cedrus.aeolion.kafkaspringpong.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Getter
@Configuration
@ComponentScan("com.cedrus.aeolion.kafkaspringpong")
@PropertySource("classpath:database.properties")
public class DBConfig {
    @Autowired private Environment env;

    private static final String DRIVER = "driver";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    @Bean
    DataSource initDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName(Objects.requireNonNull(env.getProperty(DRIVER)));
        driverManagerDataSource.setUrl(env.getProperty(URL));
        driverManagerDataSource.setUsername(env.getProperty(USER));
        driverManagerDataSource.setPassword(env.getProperty(PASSWORD));

        return driverManagerDataSource;
    }
}
