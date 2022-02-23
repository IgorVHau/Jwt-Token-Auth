package com.mycompany.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.jwtdemo.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

}
