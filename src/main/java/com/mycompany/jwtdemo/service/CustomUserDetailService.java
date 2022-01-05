package com.mycompany.jwtdemo.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService{

	//Este método faz a validação para o usuário existente
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username.equals("John")) { //validações
			return new User("John", "secret", new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User does not exist!");
		}
	}

}
