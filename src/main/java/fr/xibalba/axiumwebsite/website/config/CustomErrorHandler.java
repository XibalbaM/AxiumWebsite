package fr.xibalba.axiumwebsite.website.config;

import com.vaadin.flow.server.DefaultErrorHandler;
import com.vaadin.flow.server.ErrorEvent;

public class CustomErrorHandler extends DefaultErrorHandler {

    @Override
    public void error(ErrorEvent event) {

        super.error(event);
    }
}