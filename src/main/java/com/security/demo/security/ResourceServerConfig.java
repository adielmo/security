package com.security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.sun.jdi.Method;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailService)
           .passwordEncoder(passwordEncoder());//Esse method valida a senha q vem do BD, q está encodada, se é válida
		/*
		 * auth.inMemoryAuthentication().withUser("adielmo").password("{noop}arthur").
		 * roles("ROLE");
		 */
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		         .antMatchers("/usuarios")
		         .permitAll()
		     .anyRequest()
		         .authenticated()
		     .and()
				.sessionManagement()
				  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				 .and()
				   .csrf().disable();

	}
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		User.UserBuilder builder = User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(
				builder.username("admin")
				       .password("arthur")
				       .roles("ROLE")
				        .build());
		return manager;
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
