package com.abc_university.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abc_university.model.StudentsModel;


public interface StudentService {
	
	StudentsModel updateStudent(StudentsModel student);

	StudentsModel createStudent(StudentsModel student);

	List<StudentsModel> getAllStudents();

	Optional<StudentsModel> getStudentById(int id);

	Page<StudentsModel> getAllStudents(Pageable pageable);


}
