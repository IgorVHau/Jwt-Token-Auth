package com.mycompany.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.jwtdemo.model.JwtRequest;
import com.mycompany.jwtdemo.model.JwtResponse;
import com.mycompany.jwtdemo.service.CustomUserDetailService;
import com.mycompany.jwtdemo.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/generateToken")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) {
		
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
		//autenticar o usuário
		authenticationManager.authenticate(upat);
		
		UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
		String jwtToken = jwtUtil.generateToken(userDetails);
		
		JwtResponse jwtResponse = new JwtResponse(jwtToken);
		return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
	}

}
