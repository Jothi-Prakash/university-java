package com.university_java.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesAndPrivilegesDTO {
	
	private int userId;
	private int roleId;
	private List<Long> privileges;

}
