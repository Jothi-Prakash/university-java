package com.university_java.repository;

import java.util.Optional ;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.university_java.model.StudentsModel;


public interface StudentRepository extends JpaRepository<StudentsModel, Long> {

	Optional<StudentsModel> findById(int id);
	
	Page<StudentsModel> findByActiveTrue(Pageable pageable);

	void deleteById(int id);

	Optional<StudentsModel> findByIdAndActiveTrue(int id);


}
