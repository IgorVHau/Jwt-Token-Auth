package com.mycompany.jwtdemo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.jwtdemo.entity.UserEntity;
import com.mycompany.jwtdemo.model.UserModel;
import com.mycompany.jwtdemo.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	//Circular reference
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserModel register(UserModel userModel) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userModel, userEntity);
		//Protecting user's password through passwordEncoder
		userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
		userEntity = userRepository.save(userEntity);
		BeanUtils.copyProperties(userEntity, userModel);
		return userModel;
	}

	//This method does the validation for user existence
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Modification *
		UserEntity userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {//Here you can make a DB call with the help of repository.
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
