package fr.xibalba.axiumwebsite.website.pages.register;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("register")
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView() {

        System.out.println("RegisterView");

        setSizeFull();
        add(new H1("Test"));
    }
}