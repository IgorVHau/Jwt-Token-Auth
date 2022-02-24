package com.mycompany.jwtdemo.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.jwtdemo.entity.RoleEntity;
import com.mycompany.jwtdemo.entity.UserEntity;
import com.mycompany.jwtdemo.model.RoleModel;
import com.mycompany.jwtdemo.model.UserModel;
import com.mycompany.jwtdemo.repository.RoleRepository;
import com.mycompany.jwtdemo.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//Circular reference
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserModel register(UserModel userModel) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userModel, userEntity); //it does NOT do a deep copy
		
		Set<RoleEntity> roleEntities = new HashSet<>();
		//fetch every role from DB based on role id and than set this role to user entity roles
		for (RoleModel rm :userModel.getRoles()) {
			Optional<RoleEntity> optRole = roleRepository.findById(rm.getId());
			if(optRole.isPresent()) {
				roleEntities.add(optRole.get());
			}
		}
		userEntity.setRoles(roleEntities);
		//Protecting user's password through passwordEncoder
		userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
		userEntity = userRepository.save(userEntity);
		
		BeanUtils.copyProperties(userEntity, userModel);
		
		//convert RoleEntites to RoleModels
		Set<RoleModel> roleModels = new HashSet<>();
		RoleModel rm = null;
		for(RoleEntity re :userEntity.getRoles()) {
			rm = new RoleModel();
			rm.setRoleName(re.getRoleName());
			rm.setId(re.getId());
			roleModels.add(rm);
		}
		userModel.setRoles(roleModels);
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
		
		//convert RoleEntites to RoleModels
		Set<RoleModel> roleModels = new HashSet<>();
		RoleModel rm = null;
		for(RoleEntity re :userEntity.getRoles()) {
			rm = new RoleModel();
			rm.setRoleName(re.getRoleName());
			rm.setId(re.getId());
			roleModels.add(rm);
		}
		userModel.setRoles(roleModels);
		
		return userModel;
		
		
//		if(username.equals("John")) { //validações
//			return new User("John", "secret", new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User does not exist!");
//		}
	}

}
