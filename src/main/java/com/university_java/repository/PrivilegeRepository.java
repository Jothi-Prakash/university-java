package com.university_java.repository;

import java.util.Collection; 
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.university_java.model.PrivilegesModel;


public interface PrivilegeRepository extends JpaRepository<PrivilegesModel, Long> {

	@Query("SELECT p FROM PrivilegesModel p WHERE p.id IN :privilegeIds")
    List<PrivilegesModel> findAllById(List<Integer> privilegeIds);



	

}
