package fr.xibalba.axiumwebsite.security;

import com.vaadin.flow.server.auth.ViewAccessChecker;
import com.vaadin.flow.spring.VaadinConfigurationProperties;
import com.vaadin.flow.spring.security.RequestUtil;
import com.vaadin.flow.spring.security.VaadinDefaultRequestCache;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import fr.xibalba.axiumwebsite.website.pages.login.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends VaadinWebSecurityConfigurerAdapter {

    public static final String ROLE_QUERY = "SELECT accounts.username, roles.name " +
                                            "    FROM accounts " +
                                            "    LEFT JOIN roles " +
                                            "        ON (SELECT account_id FROM accounts_roles WHERE role_id = roles.id) = accounts.id " +
                                            "    WHERE accounts.username = ?;";

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private DataSource dataSource;


    @Autowired
    private VaadinDefaultRequestCache vaadinDefaultRequestCache;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private ViewAccessChecker viewAccessChecker;

    @Autowired
    private VaadinConfigurationProperties configurationProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM accounts WHERE username = ?")
                .authoritiesByUsernameQuery(ROLE_QUERY)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        super.configure(http);

        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/api/**");

        super.configure(web);
    }
}