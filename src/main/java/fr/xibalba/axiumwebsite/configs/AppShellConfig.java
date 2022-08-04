package fr.xibalba.axiumwebsite.configs;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Theme("main")
public class AppShellConfig extends SpringBootServletInitializer implements AppShellConfigurator {

    @Override
    public void configurePage(AppShellSettings settings) {

        AppShellConfigurator.super.configurePage(settings);
    }
}
