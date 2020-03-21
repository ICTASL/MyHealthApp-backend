package lk.gov.govtech.covid19.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static lk.gov.govtech.covid19.util.Constants.*;

@Slf4j(topic="PortalAccess")
@Configuration
@EnableWebSecurity
public class WebAuthConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PortalUserConfiguration users;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        /*
        * Endpoints without auth
        * - /application/** (all were GETs)
        * - /dhis/** (includes both POSTS and GETs)
        *
        * Endpoints with auth: either http basic auth or login (/portal) based can be used
        * - /notification/alert/add
        * - /notification/case/add
        * - /portal/**
        *
        * */
        http
            .authorizeRequests()
                .mvcMatchers( // to exclude auth for GETs
                        APPLICATION_API_CONTEXT +"/**",
                        DHIS_API_CONTEXT + "/**")
                .permitAll()
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, // to exclude auth for POSTs
                        DHIS_API_CONTEXT + "/**")
                .permitAll()
                .and()
            .csrf().disable()

            .authorizeRequests()
                .mvcMatchers(
                        "/notification/alert/add",
                        "/notification/case/add",
                        PORTAL_API_CONTEXT + "/**")
                .hasRole("USER")
                .and()
            .httpBasic()
                .and()
            .formLogin()
                .loginPage(PORTAL_API_CONTEXT)
                .permitAll()
                .successHandler(loginSuccessHandler)
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(PORTAL_API_CONTEXT + "/logout")) //logs out with a GET
                .permitAll()
                .logoutSuccessUrl(PORTAL_API_CONTEXT); //redirects once successful
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        for (String[] aUser : users.getUserCredentials()) {
            auth.inMemoryAuthentication()
                    .withUser(aUser[0])
                    .password(passwordEncoder().encode(aUser[1]))
                    .roles("USER");
        }
    }

    @Component
    protected class LoginSuccessHandler implements AuthenticationSuccessHandler {

        protected LoginSuccessHandler() {
            super();
        }


        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            User user = (User) authentication.getPrincipal();
            log.info("Portal login success, username:{}", user.getUsername());
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response,
                    PORTAL_API_CONTEXT + NEWS_PATH); //redirects once successful
        }
    }

}
