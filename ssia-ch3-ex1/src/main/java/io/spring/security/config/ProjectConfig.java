package io.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String usersByUsernameQuery = "select username, password, enabled from spring_security.users where username = ?";
        String authsByUserQuery = "select username, authority from spring_security.authorities where username = ?";

        var userDetailManager = new JdbcUserDetailsManager(dataSource);
        userDetailManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailManager.setAuthoritiesByUsernameQuery(authsByUserQuery);

        return userDetailManager;
    }

    /*
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
     */


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
