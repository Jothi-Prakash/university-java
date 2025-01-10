package com.university_java.service;

import java.util.Map;

import javax.security.sasl.AuthenticationException;

import com.university_java.DTO.LoginResponseDTO;
import com.university_java.DTO.RolesAndPrivilegesDTO;
import com.university_java.DTO.UserDTO;
import com.university_java.model.UserModel;

import jakarta.validation.Valid; 


public interface UserService {

	LoginResponseDTO login(String email, String password)throws AuthenticationException;

	UserModel registerUser(@Valid UserDTO userModel);

	void addRolesAndPrivileges(RolesAndPrivilegesDTO rolesAndPrivilegesDTO);
	


	


}
