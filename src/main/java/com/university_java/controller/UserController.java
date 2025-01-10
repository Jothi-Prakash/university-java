package com.university_java.controller;

import java.util.HashMap; 
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.university_java.DTO.LoginRequestDTO;
import com.university_java.DTO.LoginResponseDTO;
import com.university_java.DTO.RolesAndPrivilegesDTO;
import com.university_java.DTO.UserDTO;
import com.university_java.model.PrivilegesModel;
import com.university_java.model.RolesModel;
import com.university_java.model.UserModel;
import com.university_java.model.UserRolesMappingModel;
import com.university_java.model.UserRolesPrivilegesModel;
import com.university_java.repository.PrivilegeRepository;
import com.university_java.repository.RolesRepository;
import com.university_java.repository.StudentRepository;
import com.university_java.repository.UserRepository;
import com.university_java.repository.UserRolesMappingRepository;
import com.university_java.repository.UserRolesPrivilegeMappingRepository;
import com.university_java.service.EmailService;
import com.university_java.service.OtpService;
import com.university_java.service.UserService;

import jakarta.validation.Valid;

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
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	PrivilegeRepository PrivilegeRepository;
	
	@Autowired
    private OtpService otpService;
	
	@Autowired
    private EmailService emailService;
	
	@Autowired
	UserRolesMappingRepository userRolesMappingRepository;
	
	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	UserRolesPrivilegeMappingRepository userRolesPrivilegeMappingRepository;
	
	private static String generatedOtp;
	private static String userEmail;
	private static final String DEFAULT_OTP = "123456";

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
	    try {
	        
	        LoginResponseDTO response = userService.login(email, password);
	        return ResponseEntity.ok(response); 
	    } catch (AuthenticationException e) {
	       
	        log.warn("Authentication failed for email: {}", email);
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Invalid email or password");
	        
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	    } catch (Exception e) {
	        
	        log.error("Unexpected error occurred: {}", e.getMessage());
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "An unexpected error occurred");
	        
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userModel) {
	    try {
	        
	        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

	        UserModel user = userService.registerUser(userModel);
	        

	        Map<String, Object> successResponse = new HashMap<>();
	        successResponse.put("message", "User registered successfully!");
	        successResponse.put("status", "201");
	        successResponse.put("data", user);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    } catch (IllegalArgumentException e) {
	       
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", e.getMessage());
	        errorResponse.put("status", "400");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    } catch (Exception e) {
	      
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "An unexpected error occurred while registering the user");
	        errorResponse.put("status", "500");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	@GetMapping("/privileges")
	public List<PrivilegesModel> privilege() {
		
		return PrivilegeRepository.findAll();
		
	}
	
	/*
	 * @PostMapping("/sendOtp") public ResponseEntity<?> sendOTP(@RequestBody
	 * LoginRequestDTO loginRequestDto) {
	 * 
	 * Optional<UserModel> user =
	 * userRepository.findByEmail(loginRequestDto.getEmailId()); Map<String, Object>
	 * response = new HashMap<>();
	 * 
	 * if(user.isPresent()) {
	 * 
	 * generatedOtp = otpService.generateOTP();
	 * 
	 * userEmail = user.get().getEmail();
	 * 
	 * emailService.sendOTPEmail(userEmail, generatedOtp);
	 * 
	 * response.put("status", "success"); response.put("message",
	 * "Otp Send Successfully!"); return ResponseEntity.ok(response);
	 * 
	 * }else { response.put("status", "false"); response.put("message",
	 * "User EmailId Not Found!"); return ResponseEntity.status(404).body(response);
	 * } }
	 * 
	 * @PostMapping("/verify-login") public ResponseEntity<?>
	 * verifyLogin(@RequestBody LoginRequestDTO loginRequestDto) { Map<String,
	 * Object> response = new HashMap<>();
	 * 
	 * try {
	 * 
	 * String otp = loginRequestDto.getOtp(); String email =
	 * loginRequestDto.getEmailId();
	 * 
	 * if (generatedOtp != null && generatedOtp.equals(otp) &&
	 * userEmail.equals(email)) { response.put("status", "true");
	 * response.put("message", "Otp verified Successfully!"); } else {
	 * response.put("status", "false"); response.put("message", "Invalid Otp!"); }
	 * 
	 * return ResponseEntity.ok(response);
	 * 
	 * } catch (Exception e) {
	 * 
	 * response.put("status", "false"); response.put("message",
	 * "An error occurred while verifying the OTP."); response.put("error",
	 * e.getMessage()); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); } }
	 */
	

	@PostMapping("/sendOtp")
	public ResponseEntity<?> sendOTP(@RequestBody LoginRequestDTO loginRequestDto) {
	    Optional<UserModel> user = userRepository.findByEmail(loginRequestDto.getEmailId());
	    Map<String, Object> response = new HashMap<>();
	    
	    if (user.isPresent()) {
	
	        String generatedOtp = DEFAULT_OTP;

	        String userEmail = user.get().getEmail();
	        
	        emailService.sendOTPEmail(userEmail, generatedOtp);
	        
	        response.put("status", "success");
	        response.put("message", "Otp sent successfully!");
	        return ResponseEntity.ok(response);
	        
	    } else {
	        response.put("status", "false");
	        response.put("message", "User EmailId not found!");
	        return ResponseEntity.status(404).body(response);
	    }
	}

	
	@PostMapping("/verify-login")
	public ResponseEntity<?> verifyLogin(@RequestBody LoginRequestDTO loginRequestDto) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        
	        String otp = loginRequestDto.getOtp();
	        String email = loginRequestDto.getEmailId();
	        
	        Optional<UserModel> user = userRepository.findByEmail(email);

	        
	        if (DEFAULT_OTP.equals(otp)) {
	            response.put("status", "true");
	            response.put("userId", user.get().getId());
	            response.put("message", "Otp verified successfully!");
	        } else {
	            response.put("status", "false");
	            response.put("message", "Invalid OTP!");
	        }

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.put("status", "false");
	        response.put("message", "An error occurred while verifying the OTP.");
	        response.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@PostMapping("/userRolesAndPrivileges")
	public ResponseEntity<?> userRolesAndPrivileges(@RequestBody RolesAndPrivilegesDTO rolesAndPrivilegesDTO) {
	    Map<String, Object> response = new HashMap<>();

	    try {

	        userService.addRolesAndPrivileges(rolesAndPrivilegesDTO);

	        response.put("status", "true");
	        response.put("message", "Roles and privileges added successfully!");
	        return ResponseEntity.ok(response);

	    } catch (IllegalArgumentException e) {
	        response.put("status", "false");
	        response.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(response);

	    } catch (Exception e) {
	       
	        response.put("status", "false");
	        response.put("message", "An error occurred while adding roles and privileges.");
	        response.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	@GetMapping("/rolesAndPrivileges")
	public ResponseEntity<?> getUserRolesAndPrivileges(@RequestParam int userId) {
	    Map<String, Object> response = new HashMap<>();

	    // Retrieve the user by userId
	    Optional<UserModel> userOptional = userRepository.findById(userId);

	    if (userOptional.isPresent()) {
	        UserModel user = userOptional.get();

	        // Retrieve assigned privileges for the user
	        List<UserRolesPrivilegesModel> userRolesPrivileges = userRolesPrivilegeMappingRepository.findByUserId(userId);

	        List<Integer> assignedPrivilegeIds = userRolesPrivileges.stream()
	                .map(UserRolesPrivilegesModel::getPrivilegeId)
	                .distinct()
	                .collect(Collectors.toList());

	        // Retrieve all privileges and filter out the assigned ones
	        List<PrivilegesModel> allPrivileges = privilegeRepository.findAll();
	        List<String> unassignedPrivileges = allPrivileges.stream()
	                .filter(privilege -> !assignedPrivilegeIds.contains(privilege.getId()))
	                .map(PrivilegesModel::getName)
	                .collect(Collectors.toList());

	        // Retrieve assigned role for the user
	        RolesModel assignedRole = user.getRoles().stream()
	                .findFirst()
	                .orElse(null);

	        // Retrieve all roles and filter out the assigned one
	        List<RolesModel> allRoles = rolesRepository.findAll();
	        List<String> unassignedRoles = allRoles.stream()
	                .filter(role -> assignedRole == null || !role.getId().equals(assignedRole.getId()))
	                .map(RolesModel::getName)
	                .collect(Collectors.toList());

	        // Prepare response
	        response.put("email", user.getEmail());
	        response.put("assignedRole", assignedRole != null ? assignedRole.getName() : "The user has no assigned role");
	        response.put("assignedPrivileges", assignedPrivilegeIds.isEmpty() ? List.of("The user has no assigned privileges") : privilegeRepository.findAllById(assignedPrivilegeIds).stream().map(PrivilegesModel::getName).collect(Collectors.toList()));

	        return ResponseEntity.ok(response);
	    } else {
	        response.put("status", "error");
	        response.put("message", "User not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}

	
	@GetMapping("/all")
	public List<UserDTO> getAllUser() {
		List<UserModel> userModels = userRepository.findAll();
		
		List<UserDTO> userDTOs = userModels.stream()
				.map(userModel -> {
					UserDTO userDTO = new UserDTO();
					userDTO.setUserId(userModel.getId());
					userDTO.setUserName(userModel.getUserName());
					userDTO.setEmail(userModel.getEmail());
					
					return userDTO;
				})
				.collect(Collectors.toList());
		
		return userDTOs;
	
	}




	





}
