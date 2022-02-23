package com.mycompany.jwtdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.jwtdemo.model.RoleModel;
import com.mycompany.jwtdemo.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/roles")
	public RoleModel createRole(@RequestBody RoleModel roleModel) {
		return roleService.createRole(roleModel);
	}
	
	@GetMapping("/roles")
	public List<RoleModel> getAllRoles(){
		return roleService.getAllRoles();
	}
	
	@DeleteMapping("roles/{roleId}")
	public void deleteRoleById(@PathVariable Long roleId) {
		roleService.deleteRoleById(roleId);
	}

}
