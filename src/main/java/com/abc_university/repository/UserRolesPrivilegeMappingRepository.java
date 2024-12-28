package com.abc_university.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc_university.model.RolesModel;
import com.abc_university.model.UserModel;
import com.abc_university.model.UserRolesPrivilegesModel;

public interface UserRolesPrivilegeMappingRepository extends JpaRepository<UserRolesPrivilegesModel, Long> {

	List<UserRolesPrivilegesModel> findByRoleId(int roleId);

	List<UserRolesPrivilegesModel> findByUserId(int id);




}
