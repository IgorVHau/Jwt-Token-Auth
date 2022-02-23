package com.mycompany.jwtdemo.service;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycompany.jwtdemo.entity.UserEntity;
import com.mycompany.jwtdemo.model.UserModel;
import com.mycompany.jwtdemo.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	public UserModel register(UserModel userModel) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userModel, userEntity);
		userEntity = userRepository.save(userEntity);
		BeanUtils.copyProperties(userEntity, userModel);
		return userModel;
	}

	//Este método faz a validação para o usuário existente
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Modification *
		UserEntity userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			throw new UsernameNotFoundException("User does not exist!");
		}
		
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userEntity, userModel);
		
		return userModel;
		
		
//		if(username.equals("John")) { //validações
//			return new User("John", "secret", new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User does not exist!");
//		}
	}

}
