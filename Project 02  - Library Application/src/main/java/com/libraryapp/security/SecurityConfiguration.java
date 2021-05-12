package com.libraryapp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	BCryptPasswordEncoder pwEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.usersByUsernameQuery("select username, password, enabled from USERS where username = ?")
		.authoritiesByUsernameQuery("select username, role from USERS where username = ?")
		.dataSource(dataSource).passwordEncoder(pwEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/employee/**").hasRole("EMPLOYEE")
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/login/**").permitAll()
		.antMatchers("/register/**").permitAll()
		.antMatchers("/logout/**").permitAll()
		.antMatchers("/CSS/**").permitAll()
		.antMatchers("/Images/**").permitAll()
		.antMatchers("/**").authenticated().and().formLogin().loginPage("/login");
		
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().disable();
	}
	
}
