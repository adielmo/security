package com.security.demo.util;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication()
	      .withUser("admin").password("{noop}arthur").roles("ROLE");
			
			  auth.inMemoryAuthentication() .withUser("adielmo").password("arthur")
			  .roles("ROLE");
			 
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .antMatchers("/usuarios").permitAll()
		 .anyRequest().authenticated()
		    .and()
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    .and()
		 .csrf().disable();   
		   
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
	    User.UserBuilder builder = User.withDefaultPasswordEncoder();
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    manager.createUser(builder.username("admin").password("arthur")
	    		.roles("ROLE").build());
	    return manager;
	}
 */
	
}
