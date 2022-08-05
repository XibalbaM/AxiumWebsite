package fr.xibalba.axiumwebsite.website.pages;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import fr.xibalba.axiumwebsite.website.layouts.MainLayout;

@AnonymousAllowed
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout implements HasDynamicTitle {

    public HomeView() {
        setSizeFull();
        addClassName("home-view");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        add(new H1("Home"));
    }



    @Override
    public String getPageTitle() {
        return getTranslation("view.home.title");
    }
}
