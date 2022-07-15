package fr.xibalba.axiumwebsite;

import fr.xibalba.axiumwebsite.lang.Lang;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class}, scanBasePackages = {"fr.xibalba.axiumwebsite", "com.vaadin.flow.spring.security"})
public class AxiumWebsiteApplication {

    public static void main(String[] args) {

        System.setProperty("vaadin.i18n.provider", Lang.class.getName());

        SpringApplication.run(AxiumWebsiteApplication.class, args);
    }
}