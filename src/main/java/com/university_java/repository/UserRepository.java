package com.university_java.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.university_java.model.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Long> {


	Optional<UserModel> findByEmail(String emailId);

	Optional<UserModel> findByEmailAndPassword(String email, String password);

	Optional<UserModel> findById(int userId);

}
