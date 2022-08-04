package fr.xibalba.axiumwebsite;

import fr.xibalba.axiumwebsite.lang.Lang;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class}, scanBasePackages = {"fr.xibalba.axiumwebsite", "com.vaadin.flow.spring.security"})
@EnableJpaRepositories(basePackages = "fr.xibalba.axiumwebsite.api.repositories")
@EntityScan(basePackages = "fr.xibalba.axiumwebsite.api.tables")
public class AxiumWebsiteApplication {

    public static void main(String[] args) {

        System.setProperty("vaadin.i18n.provider", Lang.class.getName());

        SpringApplication.run(AxiumWebsiteApplication.class, args);
    }
}