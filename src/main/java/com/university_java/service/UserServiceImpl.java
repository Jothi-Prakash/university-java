package com.university_java.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.university_java.DTO.LoginResponseDTO;
import com.university_java.DTO.RolesAndPrivilegesDTO;
import com.university_java.DTO.UserDTO;
import com.university_java.controller.UserController;
import com.university_java.model.PrivilegesModel;
import com.university_java.model.RolesModel;
import com.university_java.model.UserModel;
import com.university_java.model.UserRolesMappingModel;
import com.university_java.model.UserRolesPrivilegesModel;
import com.university_java.repository.PrivilegeRepository;
import com.university_java.repository.RolesRepository;
import com.university_java.repository.UserRepository;
import com.university_java.repository.UserRolesMappingRepository;
import com.university_java.repository.UserRolesPrivilegeMappingRepository;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolesRepository rolesRepository;
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	UserRolesMappingRepository userRolesMappingRepository;
	
	@Autowired
	UserRolesPrivilegeMappingRepository userRolesPrivilegeMappingRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	public LoginResponseDTO login(String email, String rawPassword) throws AuthenticationException {
	    Optional<UserModel> userOptional = userRepository.findByEmail(email);

	    if (userOptional.isEmpty()) {
	        throw new AuthenticationException("Invalid email or password");
	    }

	    UserModel user = userOptional.get();

//	    log.warn("Raw password: {}", rawPassword);
//	    log.warn("Stored password (hashed): {}", user.getPassword());
//	    log.warn("PasswordEncoder.matches result: {}", passwordEncoder.matches(rawPassword, user.getPassword()));

	    if (passwordEncoder.matches(rawPassword, user.getPassword())) {
	        throw new AuthenticationException("Invalid password");
	    }

	    List<UserRolesPrivilegesModel> userRolesPrivileges = userRolesPrivilegeMappingRepository.findByUserId(user.getId());
	    List<Integer> privilegeIds = userRolesPrivileges.stream()
	            .map(UserRolesPrivilegesModel::getPrivilegeId)
	            .distinct()
	            .collect(Collectors.toList());

	    List<String> privilegeNames = privilegeRepository.findAllById(privilegeIds)
	            .stream()
	            .map(PrivilegesModel::getName)
	            .distinct()
	            .collect(Collectors.toList());

	    RolesModel role = user.getRoles().stream()
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("User does not have any role assigned"));

	    return new LoginResponseDTO(user.getEmail(), role.getName(), privilegeNames);
	}



	public UserModel registerUser(UserDTO userDTO) {
	    
	    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
	        throw new IllegalArgumentException("Email is already registered");
	    }

	    UserModel user = new UserModel();
	    user.setUserName(userDTO.getUserName());
	    String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
	    System.out.println("Hashed password: " + hashedPassword);
	    user.setPassword(hashedPassword);
	    user.setEmail(userDTO.getEmail());
	    user.setActive(true);
	    userRepository.save(user);
	    
	    return user;

	}
	
	
	public void addRolesAndPrivileges(RolesAndPrivilegesDTO rolesAndPrivilegesDTO) {
	    try {

	        Optional<UserModel> user = userRepository.findById(rolesAndPrivilegesDTO.getUserId());
	        if (!user.isPresent()) {
	            throw new IllegalArgumentException("User not found with the given ID: " + rolesAndPrivilegesDTO.getUserId());
	        }

	        UserRolesMappingModel roleMapping = new UserRolesMappingModel();
	        roleMapping.setRoleId(rolesAndPrivilegesDTO.getRoleId());
	        roleMapping.setUserId(rolesAndPrivilegesDTO.getUserId());
	        userRolesMappingRepository.save(roleMapping);

	        List<PrivilegesModel> privileges = privilegeRepository.findAllById(rolesAndPrivilegesDTO.getPrivileges());
	        if (privileges.size() != rolesAndPrivilegesDTO.getPrivileges().size()) {
	            throw new IllegalArgumentException("Some privilege IDs are invalid.");
	        }

	        for (PrivilegesModel privilege : privileges) {
	            UserRolesPrivilegesModel privilegeMapping = new UserRolesPrivilegesModel();
	            privilegeMapping.setRoleId(rolesAndPrivilegesDTO.getRoleId());
	            privilegeMapping.setUserId(rolesAndPrivilegesDTO.getUserId());
	            privilegeMapping.setPrivilegeId(privilege.getId());

	            userRolesPrivilegeMappingRepository.save(privilegeMapping);
	        }

	    } catch (IllegalArgumentException e) {
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("An unexpected error occurred while adding roles and privileges.", e);
	    }
	}

		
	



	

	

	

	
	


	


	

}
