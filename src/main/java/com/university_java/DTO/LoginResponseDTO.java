package com.university_java.DTO;

import java.util.List; 
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

	
	 private String email;
     private String role;
     private List<String> privileges;

	
}
