package server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import server.service.UserService;

@Configuration
@EnableWebSecurity
public class LibrarySecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.httpBasic();
		
		httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
		httpSecurity.authorizeRequests().antMatchers("/libreria/autor/{username}/**").access("hasRole('AUTHOR') and #username == principal.username");
		httpSecurity.authorizeRequests().antMatchers("/libreria/editor/**").hasRole("EDITOR");
		httpSecurity.authorizeRequests().antMatchers("/libreria/revisor/{username}/**").access("hasRole('REVISER') and #username == principal.username");
		httpSecurity.authorizeRequests().antMatchers("/libreria/usuario/{username}/login").access("#username == principal.username");
		httpSecurity.authorizeRequests().antMatchers("/libreria/**").permitAll();
	
	}
}
