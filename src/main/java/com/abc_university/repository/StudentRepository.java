package com.abc_university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc_university.model.StudentsModel;

public interface StudentRepository extends JpaRepository<StudentsModel, Long> {

	Optional<StudentsModel> findById(int id);

}
