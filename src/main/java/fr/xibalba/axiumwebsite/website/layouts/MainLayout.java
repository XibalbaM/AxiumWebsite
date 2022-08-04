package fr.xibalba.axiumwebsite.website.layouts;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import fr.xibalba.axiumwebsite.security.SecurityService;
import fr.xibalba.axiumwebsite.website.pages.HomeView;
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

        HorizontalLayout headerLeft = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        headerLeft.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerLeft.addClassNames("py-0", "px-m");

        HorizontalLayout headerRight = new HorizontalLayout();
        headerRight.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            logout.addClassName("m-m");

            headerRight.add(logout);
        } else {
            Button login = new Button("Login", click ->
                    UI.getCurrent().navigate("login"));
            login.addClassName("m-m");

            Button register = new Button("Register", click ->
                    UI.getCurrent().navigate("register"));
            register.addClassName("m-m");

            headerRight.add(register, login);
        }

        HorizontalLayout header = new HorizontalLayout(
                headerLeft,
                headerRight
        );

        header.setWidth("100%");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        addToNavbar(header);
    }

    private void createDrawer() {

        RouterLink home = new RouterLink("", HomeView.class);
        home.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(home));
    }
}