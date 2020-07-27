package com.inventory.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class InventorySecurity extends WebSecurityConfigurerAdapter {

	// Authentication : User --> Roles
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("avinash").password("{noop}avinash1").roles("USER").and()
				.withUser("adminavinash").password("{noop}adminavinash1").roles("USER", "ADMIN");
	}

	// Authorization : Role -> Access
	// survey -> USER
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/surveys/**").hasRole("USER").antMatchers("/users/**")
				.hasRole("USER").antMatchers("/**").hasRole("ADMIN").and().csrf().disable().headers().frameOptions()
				.disable();
	}

}