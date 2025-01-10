package com.university_java.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
	
	private String emailId;
	private String password;
	private String otp;
	private String captchaResponse;

}
