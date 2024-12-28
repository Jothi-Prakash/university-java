package com.abc_university.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc_university.DTO.LoginRequestDTO;
import com.abc_university.DTO.LoginResponseDTO;
import com.abc_university.model.PrivilegesModel;
import com.abc_university.model.RolesModel;
import com.abc_university.model.UserModel;
import com.abc_university.repository.RolesRepository;
import com.abc_university.repository.UserRepository;
import com.abc_university.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	RolesRepository rolesRepository;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestParam String email, @RequestParam String password) {
		LoginResponseDTO response = userService.login(email, password);
		return ResponseEntity.ok(response);
	}



}
