package com.university_java.service;

import java.util.List; 
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.university_java.model.StudentsModel;



public interface StudentService {
	
	StudentsModel updateStudent(StudentsModel student);

	StudentsModel createStudent(StudentsModel student);

	Optional<StudentsModel> getStudentById(int id);

	Page<StudentsModel> getAllStudents(Pageable pageable);


}
