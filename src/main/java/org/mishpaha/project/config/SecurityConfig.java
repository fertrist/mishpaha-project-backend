package org.mishpaha.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;

import static java.lang.String.format;
import static org.mishpaha.project.config.Constants.*;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SECRET = "53cr3t";
    @Autowired
    private DataSource dataSource;
    private final static String usersTable = "users";
    private final static String userRolesTable = "user_roles";

    /**
     * Override to configure user store.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from " + usersTable + " where username=?")
            .authoritiesByUsernameQuery("select username, role from " + userRolesTable + " where username=?")
        //passwords should be written to database with the same encoding. Now they are loaded by init script
        .passwordEncoder(new StandardPasswordEncoder(SECRET))
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String template = "hasAnyRole('ROLE_%s_'.concat(#id), 'ADMIN')";
        final String group_role = format(template, "GROUP");
        final String region_role = format(template, "REGION");
        final String tribe_role = format(template, "TRIBE");
        http
            .formLogin().loginPage("/").and()
            .httpBasic().and()
            .authorizeRequests()
                .antMatchers("/home.html", "/login.html", "/", "/js/**", "/css/**").permitAll()
                .antMatchers(PEOPLE_BASE + GROUP_ID + "/**").access(group_role)
                .antMatchers(PEOPLE_BASE + REGION_ID + "/**").access(region_role)
                .antMatchers(PEOPLE_BASE + TRIBE_ID + "/**").access(tribe_role)

                .antMatchers(EVENTS_BASE + GROUP_ID).access(group_role)
                .antMatchers(EVENTS_BASE + REGION_ID).access(region_role)
                .antMatchers(EVENTS_BASE + TRIBE_ID).access(tribe_role)

                .antMatchers(REPORTS_BASE + REGION_ID).access(region_role)
                .antMatchers(REPORTS_BASE + GROUP_ID).access(group_role)
                .antMatchers(REPORTS_BASE + TRIBE_ID).access(tribe_role)

                .antMatchers(SECURITY_BASE + USERS, SECURITY_BASE + USERS + "/**").access("hasRole('ADMIN')")
            .anyRequest().authenticated().and()
            .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .csrf().csrfTokenRepository(csrfTokenRepository());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}