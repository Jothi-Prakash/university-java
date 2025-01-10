package com.university_java.DTO;

import java.util.List;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private int userId;

	private String userName;

	@Transient
	private String password;

	private String email;

}
