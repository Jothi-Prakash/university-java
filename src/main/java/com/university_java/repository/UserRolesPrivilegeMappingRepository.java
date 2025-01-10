package com.university_java.repository;

import java.util.List; 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university_java.model.UserRolesPrivilegesModel;

public interface UserRolesPrivilegeMappingRepository extends JpaRepository<UserRolesPrivilegesModel, Long> {

	List<UserRolesPrivilegesModel> findByRoleId(int roleId);

	List<UserRolesPrivilegesModel> findByUserId(int id);




}
