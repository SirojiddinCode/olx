package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // return NoOpPasswordEncoder.getInstance();
       /* return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return "aaa";
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return true;
            }
        };*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/category/**").permitAll()
                .antMatchers(HttpMethod.GET,"/region/**").permitAll()
                .antMatchers(HttpMethod.GET,"/attach/**", "/post/**").permitAll()
                .antMatchers("/attach/upload","/attach/delete","/attach/update")
                .hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();

    }
}
