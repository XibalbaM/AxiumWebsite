package fr.xibalba.axiumwebsite.website.config;

import com.vaadin.flow.server.*;

import javax.servlet.ServletException;

public class CustomServlet extends VaadinServlet {

    @Override
    protected void servletInitialized() throws ServletException {

        super.servletInitialized();

        getService().setSystemMessagesProvider(
                new SystemMessagesProvider() {

                    @Override
                    public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {

                        CustomizedSystemMessages messages =
                                new CustomizedSystemMessages();



                        return messages;
                    }
                });
    }
}