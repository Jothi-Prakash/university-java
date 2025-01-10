package com.university_java.service;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.university_java.model.StudentsModel;
import com.university_java.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Override
	public StudentsModel updateStudent(StudentsModel student) {
	    Optional<StudentsModel> existingStudentOpt = studentRepository.findById(student.getId());

	    if (existingStudentOpt.isPresent()) {
	        StudentsModel existingStudent = existingStudentOpt.get();

	        // Update only the fields that are provided (non-null and non-empty)
	        if (student.getFirstName() != null && !student.getFirstName().isEmpty()) {
	            existingStudent.setFirstName(student.getFirstName());
	        }
	        if (student.getLastName() != null && !student.getLastName().isEmpty()) {
	            existingStudent.setLastName(student.getLastName());
	        }
	        if (student.getEmail() != null && !student.getEmail().isEmpty()) {
	            existingStudent.setEmail(student.getEmail());
	        }
	        if (student.getDob() != null && !student.getDob().isEmpty()) {
	            existingStudent.setDob(student.getDob());
	        }
	        if (student.getState() != null && !student.getState().isEmpty()) {
	            existingStudent.setState(student.getState());
	        }
	        if (student.getCity() != null && !student.getCity().isEmpty()) {
	            existingStudent.setCity(student.getCity());
	        }
	        if (student.getDegree() != null && !student.getDegree().isEmpty()) {
	            existingStudent.setDegree(student.getDegree());
	        }

	        return studentRepository.save(existingStudent);
	    }

	    throw new RuntimeException("Student not found for update.");
	}


	@Override
	public StudentsModel createStudent(StudentsModel student) {
		return studentRepository.save(student);

	}

	@Override
	public Optional<StudentsModel> getStudentById(int id) {
		return studentRepository.findByIdAndActiveTrue(id);
	}

	@Override
	public Page<StudentsModel> getAllStudents(Pageable pageable) {
		// TODO Auto-generated method stub
		return studentRepository.findByActiveTrue(pageable);
		
	}

}
