package fr.xibalba.axiumwebsite.website.pages.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import fr.xibalba.axiumwebsite.api.repositories.AccountRepository;
import fr.xibalba.axiumwebsite.api.tables.Account;
import fr.xibalba.axiumwebsite.security.SecurityService;
import fr.xibalba.axiumwebsite.website.layouts.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "register", layout = MainLayout.class)
@AnonymousAllowed
@ComponentScan("fr.xibalba.axiumwebsite.api.repositories")
public class RegisterView extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {

    RegisterForm registerForm = new RegisterForm();

    public RegisterView(@Autowired SecurityService securityService) {

        if (securityService.getAuthenticatedUser() != null) {

            UI.getCurrent().navigate("");
            return;
        }

        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(false);

        add(new H1("Temporary Page"), registerForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    }

    @Override
    public String getPageTitle() {

        return getTranslation("view.register.title");
    }

    private static class RegisterForm extends VerticalLayout {

        private final TextField username;
        private final EmailField email;
        private final PasswordField password;
        private final PasswordField confirmPassword;
        private final Upload icon;
        private final Button register;

        @Autowired(required = true)
        private AccountRepository accountRepository;

        @Autowired
        private AuthenticationManager authenticationManager;

        public RegisterForm() {

            setAlignItems(Alignment.CENTER);
            setJustifyContentMode(JustifyContentMode.CENTER);

            H1 title = new H1("Register");

            username = new TextField("Username");
            username.setRequired(true);
            username.addValueChangeListener(event -> onValueChange());
            username.setErrorMessage("Please provide an username between 3 and 30 characters");

            email = new EmailField("Email");
            email.addValueChangeListener(event -> onValueChange());
            email.setErrorMessage("Please provide a valid email address");

            password = new PasswordField("Password");
            password.setRequired(true);
            password.addValueChangeListener(event -> onValueChange());
            password.setErrorMessage("Please provide a password of at least 8 characters");

            confirmPassword = new PasswordField("Confirm Password");
            confirmPassword.setRequired(true);
            confirmPassword.addValueChangeListener(event -> onValueChange());
            confirmPassword.setErrorMessage("Passwords must match");

            FileBuffer fileBuffer = new FileBuffer();
            icon = new Upload(fileBuffer);
            icon.setAcceptedFileTypes("image/*");
            icon.setMaxFileSize(1_048_576);
            icon.setMaxFiles(1);
            icon.addAllFinishedListener(event -> onValueChange());
            icon.addFailedListener(event -> onValueChange());
            icon.addStartedListener(event -> onValueChange());
            icon.addSucceededListener(event -> onValueChange());
            icon.addFileRejectedListener(event -> onValueChange());

            register = new Button("Register");
            register.setEnabled(false);
            register.addClickListener(event -> {

                Account account = Account.create(username.getValue(), email.getValue(), password.getValue(), fileBuffer.getFileData() == null ? null : fileBuffer.getFileData().getFile().getAbsolutePath());

                accountRepository.save(account);

                SecurityContextHolder.getContext().setAuthentication(
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), password.getValue())));

                UI.getCurrent().navigate("");
            });

            add(title, username, email, password, confirmPassword, icon, register);
        }

        private void onValueChange() {

            boolean enabled = true;

            if (username.getValue().length() < 3 || username.getValue().length() > 30) {

                enabled = false;
                email.setInvalid(true);
            } else {

                email.setInvalid(false);
            }

            if (email.isInvalid()) {

                enabled = false;
                email.setInvalid(true);
            } else {

                email.setInvalid(false);
            }

            if (password.getValue().length() < 8 && password.getValue().length() > 0) {

                enabled = false;
                password.setInvalid(true);
            } else {

                password.setInvalid(false);
            }

            if (!password.getValue().equals(confirmPassword.getValue())) {

                enabled = false;
                confirmPassword.setInvalid(true);
            } else {

                confirmPassword.setInvalid(false);
            }
            if (icon.isUploading()) {

                enabled = false;
            }

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

                enabled = false;
            }

            register.setEnabled(enabled);
        }
    }
}