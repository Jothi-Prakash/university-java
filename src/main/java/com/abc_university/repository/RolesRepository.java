package com.abc_university.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc_university.model.RolesModel;
import com.abc_university.model.UserRolesPrivilegesModel;

public interface RolesRepository extends JpaRepository<RolesModel, Long> {

	Optional<RolesModel> findById(int roleId);

	

}
