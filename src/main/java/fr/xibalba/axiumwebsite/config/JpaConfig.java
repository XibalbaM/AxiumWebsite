package fr.xibalba.axiumwebsite.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Bean
    public DataSource getDataSource() {

        return DataSourceBuilder.create()
                .url("jdbc:postgresql://ec2-63-34-180-86.eu-west-1.compute.amazonaws.com:5432/degfa1cmj6u5tk")
                .username("emxmxutnrqwqlk")
                .password("7d001c6bb56b1eeb8bf599474e21422c08add011ca7114da44b89ebcbd6c34b9").build();
    }
}