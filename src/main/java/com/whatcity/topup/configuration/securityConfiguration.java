package com.whatcity.topup.configuration;

import com.whatcity.topup.steam.service.CustomerDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class securityConfiguration extends WebSecurityConfigurerAdapter implements
  WebMvcConfigurer {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.cors().disable().csrf().disable().authorizeRequests()
      .mvcMatchers("/index", "/news", "/help", "/contact", "/content/**", "/news/**")
      .permitAll()
      .antMatchers("/scb/payment/**").permitAll()
      .antMatchers("/js/**", "/img/**", "/fonts/**", "/css/**", "/bower_components/**").permitAll()
      .antMatchers("/resources/**", "/webjars/**", "/built/**", "/static/**").permitAll()
      .and()
      .authorizeRequests()
      .anyRequest().authenticated()
      .and()
      .openidLogin()
      .loginPage("/").permitAll()
      .authenticationUserDetailsService(authenticationUserDetailsService());
  }

  @Bean
  public AuthenticationUserDetailsService<OpenIDAuthenticationToken> authenticationUserDetailsService() {
    return new CustomerDetailService();
  }
}
