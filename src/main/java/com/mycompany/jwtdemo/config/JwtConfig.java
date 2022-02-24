package com.mycompany.jwtdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mycompany.jwtdemo.filter.JwtAuthenticationFilter;
import com.mycompany.jwtdemo.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class JwtConfig extends WebSecurityConfigurerAdapter {
	
	//It's here that starts a circular reference issue
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationFilter jwtFilter;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	//Aqui dizemos como nós queremos gerenciar o processo de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService)
		//Using the new passwordEncoder inside the AuthenticationManager
		.passwordEncoder(passwordEncoder());
	}

	//Com este método, controlamos quais endpoints são permitidos e quais não são.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().disable()
			.authorizeRequests()
			.antMatchers("/api/login", "/api/register", "/h2-console/**").permitAll()
			.anyRequest().authenticated()//for any other request, authentication should be performed
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	//Put a different (and better) password right here
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	

}
