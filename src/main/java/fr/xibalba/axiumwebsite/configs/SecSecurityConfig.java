package fr.xibalba.axiumwebsite.configs;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

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

    private void authenticationFailureHandler(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse, AuthenticationException e) {

        System.out.println("Authentication failure");
        e.printStackTrace();
    }

    @SneakyThrows
    private void authenticationSuccessHandler(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse, Authentication authentication) {

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/user/home");
    }

    @SneakyThrows
    private void logoutSuccessHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                      Authentication authentication) {

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/");
    }

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

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/api/**",
                        "/favicon.ico", "/error", "/vendor/**", "/css/**",
                        "/fonts/**", "/images/**", "/js/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/login", "/register").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .successHandler(this::authenticationSuccessHandler)
                .failureUrl("/login?error=true")
                .failureHandler(this::authenticationFailureHandler)
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(this::logoutSuccessHandler)
                .and()
                .httpBasic()
                .and();
    }
}