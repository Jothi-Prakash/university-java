package com.abc_university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc_university.model.UserRolesMappingModel;

public interface UserRolesMappingRepository extends JpaRepository<UserRolesMappingModel, Long> {

	UserRolesMappingModel findByUserId(int id);

}
