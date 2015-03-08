package org.diploma.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                            "SELECT " +
                                    "login AS username," +
                                    "password," +
                                    "enabled " +
                            "FROM [user] " +
                            "WHERE login = ?"
                    )
                    .authoritiesByUsernameQuery(
                            "SELECT " +
                                    "u.login AS username," +
                                    "ur.name AS authority " +
                            "FROM [user] u " +
                            "JOIN userRole ur ON ur.id = u.roleId " +
                            "WHERE u.login = ?"
                    );
//                .inMemoryAuthentication()
//                    .withUser("user1")
//                        .password("123")
//                        .roles("USER")
//                        .and()
//                    .withUser("user2")
//                        .password("123")
//                        .roles("USER")
//                        .and()
//                    .withUser("admin")
//                        .password("123")
//                        .roles("USER", "ADMIN");
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/**").hasRole("USER")
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/documents/")
                    .loginProcessingUrl("/check-credentials")
                    .and()
                .logout()
                    .logoutSuccessUrl("/login");
    }
}
