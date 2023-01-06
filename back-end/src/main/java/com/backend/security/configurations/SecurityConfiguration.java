package com.backend.security.configurations;

import com.backend.security.filters.JwtRequestFilter;
import com.backend.security.services.MyUserDetailsService;
import com.backend.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/logout/**").permitAll()
                .antMatchers(HttpMethod.POST,"/users/STUDENT").permitAll()
                .antMatchers(HttpMethod.POST,"/users/TEACHER").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/users/TEACHER").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/users/STUDENT").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/users/check-email/*").permitAll()
                .antMatchers(HttpMethod.PUT,"/users/reactivate-teacher/*").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/users/*").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/questions").hasAuthority(UserType.TEACHER.name())
                .antMatchers(HttpMethod.GET, "/questions").hasAnyAuthority(UserType.ADMIN.name(), UserType.TEACHER.name())
                .antMatchers(HttpMethod.PUT, "/questions").hasAuthority(UserType.TEACHER.name())
                .antMatchers(HttpMethod.PUT, "/questions/author/*/*").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/questions/*").hasAnyAuthority(UserType.ADMIN.name(), UserType.TEACHER.name())
                .antMatchers(HttpMethod.GET, "/charts/users").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/charts/new-users").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/charts/logins").hasAuthority(UserType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/charts/responses").hasAnyAuthority(UserType.TEACHER.name(), UserType.STUDENT.name())
                .antMatchers(HttpMethod.GET, "/charts/questions-per-day").hasAnyAuthority(UserType.TEACHER.name(), UserType.STUDENT.name())
                .antMatchers("/attempts/**").hasAnyAuthority(UserType.STUDENT.name())
                .antMatchers("/exams/**").hasAnyAuthority(UserType.STUDENT.name())
                .anyRequest().authenticated().and()
                .exceptionHandling().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
