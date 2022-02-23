package com.mycompany.jwtdemo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
//It makes sure that, for a particular request, this filter is only going to get call once.
import org.springframework.web.filter.OncePerRequestFilter;

import com.mycompany.jwtdemo.service.CustomUserDetailService;
import com.mycompany.jwtdemo.util.JwtUtil;

//Call this filter only once per request
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	//This method is going to get called before our Controller is called.
	//Before the controller matters, any method, this filter will get executed
	@Override
	protected void doFilterInternal(
			HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse, 
			FilterChain filterChain
			)
		throws ServletException, IOException {
		//get the JWT token from request header
		//validate that jwt token
			String bearerToken = httpServletRequest.getHeader("Authorization");
			String username = null;
			String token = null;
				
			//Check if token exists or has Bearer text
			if(bearerToken != null && bearerToken.startsWith("Bearer")) {
			// extract JWT token from bearerToken
				token = bearerToken.substring(7); //after the sixth, because 7 doesn't count
					
				try {
					//Extract username from the token
					username = jwtUtil.extractUsername(token);
					
					//get userdetails for this user
					UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
					
					//Security checks
					if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						
						//Create object authentication - standard code
						UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
						
						SecurityContextHolder.getContext().setAuthentication(upat);
						
					}else {
						System.out.println("Invalid Token!!");
					}
						
				}catch (Exception ex) {
					ex.printStackTrace();
				}
		}else {
			System.out.println("Invalid Bearer Token Format!!");
		}
			
			//if all is well forward, the filter request to the request endpoint
			filterChain.doFilter(httpServletRequest, httpServletResponse);
	}


	
		
	
	
	

}
