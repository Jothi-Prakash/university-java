package com.university_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university_java.model.UserRolesMappingModel; 


public interface UserRolesMappingRepository extends JpaRepository<UserRolesMappingModel, Long> {

	UserRolesMappingModel findByUserId(int id);

}
