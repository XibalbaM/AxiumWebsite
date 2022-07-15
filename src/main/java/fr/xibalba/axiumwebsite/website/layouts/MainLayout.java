package fr.xibalba.axiumwebsite.website.layouts;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.xibalba.axiumwebsite.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(@Autowired SecurityService securityService) {

        this.securityService = securityService;

        createHeader();
        createDrawer();
    }

    private void createHeader() {

        H1 logo = new H1("Axium Centrality");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            header = new HorizontalLayout(logo, logout);
        }

        addToNavbar(header);
    }

    private void createDrawer() {

        addToDrawer(new VerticalLayout());
    }
}