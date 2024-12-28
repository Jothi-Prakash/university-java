package com.abc_university.service;

import java.time.LocalDateTime; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.abc_university.DTO.LoginResponseDTO;
import com.abc_university.model.PrivilegesModel;
import com.abc_university.model.RolesModel;
import com.abc_university.model.UserModel;
import com.abc_university.model.UserRolesMappingModel;
import com.abc_university.model.UserRolesPrivilegesModel;
import com.abc_university.repository.PrivilegeRepository;
import com.abc_university.repository.RolesRepository;
import com.abc_university.repository.UserRepository;
import com.abc_university.repository.UserRolesMappingRepository;
import com.abc_university.repository.UserRolesPrivilegeMappingRepository;

import jakarta.transaction.Transactional;

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
	JavaMailSender mailSender;

	@Override
	public LoginResponseDTO login(String email, String password) {
        // Step 1: Fetch user by email and password
        Optional<UserModel> userOptional = userRepository.findByEmailAndPassword(email, password);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        UserModel user = userOptional.get();

        // Step 2: Fetch all privilege mappings for the user
        List<UserRolesPrivilegesModel> userRolesPrivileges = userRolesPrivilegeMappingRepository.findByUserId(user.getId());

        // Step 3: Extract unique privilege IDs from the mapping
        List<Long> privilegeIds = userRolesPrivileges.stream()
                .map(UserRolesPrivilegesModel::getPrivilegeId) // Get privilegeId from the mapping
                .distinct() // Ensure privilege IDs are unique
                .collect(Collectors.toList());

        // Step 4: Fetch privilege names using the privilege IDs
        List<String> privilegeNames = privilegeRepository.findAllById(privilegeIds)
                .stream()
                .map(PrivilegesModel::getName) // Extract privilege names from PrivilegesModel
                .distinct() // Ensure privilege names are unique
                .collect(Collectors.toList());

        // Step 5: Fetch user's role (assuming single role per user)
        RolesModel role = user.getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User does not have any role assigned"));

        // Step 6: Return a structured response
        return new LoginResponseDTO(user.getEmail(), role.getName(), privilegeNames);
    }

	

	

	

	
	


	


	

}
