package lk.gov.govtech.covid19.security;

import lk.gov.govtech.covid19.config.PortalUserConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;

import static lk.gov.govtech.covid19.util.Constants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    /*
     * Endpoints without auth
     * - /application/** (all were GETs)
     * - /dhis/** (includes both POSTS and GETs)
     *
     * Endpoints with auth: either http basic auth or login (/portal) based can be used
     * - /notification/alert/add
     * - /notification/case/add
     *
     * */

    @Autowired
    PortalUserConfiguration users;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Bean //An exposed user details bean which both the following WebSecurityConfigurerAdapters use
    public UserDetailsService userDetailsService() throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        for (String[] aUser : users.getUserCredentials()) {
            manager.createUser(User.builder()
                        .username(aUser[0])
                        .password(passwordEncoder().encode(aUser[1]))
                        .authorities(AUTHORITY_NOTIFICATION).build());
        }
        return manager;
    }

    @Configuration
    @Order(1)
    public static class StatelessSecurityConfig extends WebSecurityConfigurerAdapter {
        /*
        *  This section
        *       - excludes /application/** and /dhis/** from auth
        *       - allows requests with http-basic-auth to be STATELESS
        * */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            http
                .authorizeRequests()
                    .mvcMatchers( // to exclude auth for GETs
                            APPLICATION_API_CONTEXT + "/**",
                            DHIS_API_CONTEXT + "/**")
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, // to exclude auth for POSTs
                            DHIS_API_CONTEXT + "/**")
                    .permitAll()
                    .and()
                .csrf().disable()
                .requestMatcher(new RequestMatcher() {
                    @Override
                    public boolean matches(HttpServletRequest request) {
                        return request.getHeader("Authorization") != null;
                    }
                })
                .httpBasic()
                    .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    @Order(2)
    public static class StatefulSecurityConfig extends WebSecurityConfigurerAdapter {
        /*
         *  This section
         *       - adds auth to some paths, that are common to both basic-auth & auth-token (i.e. stateless and stateful)
         *       - creates a POST endpoint at path /auth (to get a token)
         *       - created a GET endpoint at path /auth/logout (to immediately delete session)
         *       - stops saving anonymous requests in sessions
         * */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            http
                .csrf().disable()
                .authorizeRequests() // Common to both: stateless & stateful. Only the paths and the authority matters
                    .mvcMatchers(
                            "/notification/alert/add",
                            "/notification/case/add")
                    .hasAuthority(AUTHORITY_NOTIFICATION)
                    .and()
                .addFilter(getPasswordFilter())
                .requestCache() // stops saving anonymous requests in sessions
                    .requestCache(new NullRequestCache())
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(AUTH_API_CONTEXT + "/logout")) //deletes session immediately
                    .permitAll();
        }

        private UsernamePasswordAuthenticationFilter getPasswordFilter() throws Exception {
            UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
            filter.setFilterProcessesUrl(AUTH_API_CONTEXT);
            filter.setAuthenticationSuccessHandler(new SessionAuthenticationSuccessHandler());
            filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
            filter.setAuthenticationManager(this.authenticationManagerBean());
            return filter;
        }
    }
}
