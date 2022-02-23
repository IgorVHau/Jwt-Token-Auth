package com.mycompany.jwtdemo.service;

import java.util.List;

import com.mycompany.jwtdemo.model.RoleModel;

public interface RoleService {
	
	public RoleModel createRole(RoleModel roleModel);
	public List<RoleModel> getAllRoles();
	public RoleModel getRoleById(Long roleId);
	public void deleteRoleById(Long roleId);

}
