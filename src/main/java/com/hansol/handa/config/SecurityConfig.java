package com.hansol.handa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	// URL 별 권한관리 설정
        http
        .authorizeRequests()
        .antMatchers("/", "/list", "/detail", "/comment", "/member/login", "/member/register", "/assets/**").permitAll();
//        .anyRequest().authenticated();

        // 로그인 페이지 설정
        http
        .formLogin()
        .loginPage("/member/login")
        .usernameParameter("member_id")
        .passwordParameter("password");

    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
