package fr.xibalba.axiumwebsite.website.pages.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import fr.xibalba.axiumwebsite.security.SecurityService;
import fr.xibalba.axiumwebsite.website.layouts.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login", layout = MainLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {

    private final LoginForm login = new LoginForm();

    public LoginView(@Autowired SecurityService securityService) {

        if (securityService.getAuthenticatedUser() != null) {

            UI.getCurrent().navigate("");
            return;
        }

        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.setAction("login");

        add(new H1("Temporary Page"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    @Override
    public String getPageTitle() {

        return getTranslation("view.login.title");
    }
}
