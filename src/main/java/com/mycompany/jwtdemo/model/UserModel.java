package com.mycompany.jwtdemo.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mycompany.jwtdemo.entity.RoleEntity;
import com.mycompany.jwtdemo.entity.UserEntity;
import com.mycompany.jwtdemo.repository.UserRepository;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel implements UserDetails {
	
///**
//	 * 
//	 */
//	private static final long serialVersionUID = -6574647225724205935L;
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Set<RoleModel> roles = new HashSet<>();
//	After modifications * in CustomUserDetailService
//	@Autowired
//	private UserRepository userRepository;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Set<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}
	
	//Adding methods
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//After modifying * CustomUserDetailService
//		UserEntity userEntity = userRepository.findByUsername(username);
		Set<AuthorityModel> authorityModels = new HashSet<>();
//		userEntity.getRoles().forEach( role -> {
		roles.forEach( role -> {
				authorityModels.add(new AuthorityModel(role.getRoleName()));
		});
		return authorityModels;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
