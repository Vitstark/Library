package org.example.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:testdb.properties")
public class TestConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getProperty("driver"));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("name"));
        dataSource.setPassword(environment.getProperty("password"));

        return dataSource;
    }
}
