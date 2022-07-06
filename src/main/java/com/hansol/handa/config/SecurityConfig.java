package com.hansol.handa.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.hansol.handa.security.CustomLoginSuccessHandler;
import com.hansol.handa.security.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserService())
        .passwordEncoder(passwordEncoder());
	}
	@Bean
	UserDetailsService customUserService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
    AuthenticationSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();
    	
    	// URL 별 권한관리 설정
        http.authorizeRequests()
        .antMatchers(
        		"/", "/list", "/detail", "/comment", "/member/login", 
        		"/member/register", "/assets/**", "/smarteditor/**",
        		"/member/index").permitAll()
        .antMatchers("/mypage/**", "/create", "/member/amend/**","/challenge-amend/**","/challenge/member").access("hasRole('ROLE_USER')");
        // .access("hasRole('ROLE_USER')")
//        .anyRequest().authenticated();

        // 로그인 페이지 설정
        http.formLogin()
        .loginPage("/member/login")
        .loginProcessingUrl("/login")
        .successHandler(loginSuccessHandler());
        
        http.logout()
        .logoutUrl("/member/logout")
        .invalidateHttpSession(true)
        .deleteCookies("remember-me", "JESSION_ID");
        
        http.rememberMe()
	      .key("handa")
	      .tokenRepository(persistentTokenRepository())
	      .tokenValiditySeconds(604800); // 일주일

    }
    

    @Bean
	PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
    

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
