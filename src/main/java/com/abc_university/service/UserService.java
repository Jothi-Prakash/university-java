package com.abc_university.service;

import java.util.Map;

import com.abc_university.DTO.LoginResponseDTO;
import com.abc_university.model.UserModel;

public interface UserService {

	LoginResponseDTO login(String email, String password);
	


	


}
