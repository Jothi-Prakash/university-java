package com.abc_university.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abc_university.model.PrivilegesModel;
import com.abc_university.model.RolesModel;
import com.abc_university.model.UserModel;
import com.abc_university.model.UserRolesPrivilegesModel;

public interface PrivilegeRepository extends JpaRepository<PrivilegesModel, Long> {

	@Query("SELECT p FROM PrivilegesModel p WHERE p.id IN :privilegeIds")
    List<PrivilegesModel> findAllById(List<Long> privilegeIds);



	

}
