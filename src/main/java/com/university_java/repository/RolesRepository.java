package com.university_java.repository;

import java.util.Collection; 
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university_java.model.RolesModel;


public interface RolesRepository extends JpaRepository<RolesModel, Long> {

	Optional<RolesModel> findById(int roleId);

	

}
