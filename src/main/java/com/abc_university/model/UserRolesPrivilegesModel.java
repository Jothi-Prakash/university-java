package com.abc_university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_roles_privileges_mapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesPrivilegesModel {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "role_id")
	private int roleId;
	
	@Column(name = "privilege_id")
	private Long privilegeId;
	

}
