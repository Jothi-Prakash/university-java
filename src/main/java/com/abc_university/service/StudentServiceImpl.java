package com.abc_university.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abc_university.model.StudentsModel;
import com.abc_university.repository.StudentRepository;


@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Override
	public StudentsModel updateStudent(StudentsModel student) {
		Optional<StudentsModel> existingStudentOpt = studentRepository.findById(student.getId());

		if (existingStudentOpt.isPresent()) {
			StudentsModel existingStudent = existingStudentOpt.get();

			existingStudent.setFirstName(student.getFirstName());
			existingStudent.setLastName(student.getLastName());
			existingStudent.setEmail(student.getEmail());
			existingStudent.setDob(student.getDob());
			existingStudent.setState(student.getState());
			existingStudent.setCity(student.getCity());
			existingStudent.setDegree(student.getDegree());

			return studentRepository.save(existingStudent);
		}

		throw new RuntimeException("Student not found for update.");
	}

	@Override
	public StudentsModel createStudent(StudentsModel student) {
		return studentRepository.save(student);

	}

	@Override
	public List<StudentsModel> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Optional<StudentsModel> getStudentById(int id) {
		return studentRepository.findById(id);
	}

	@Override
	public Page<StudentsModel> getAllStudents(Pageable pageable) {
		// TODO Auto-generated method stub
		return studentRepository.findAll(pageable);
	}

}
