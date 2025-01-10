package com.university_java.controller;

import java.util.HashMap;  
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.university_java.model.StudentsModel;
import com.university_java.repository.StudentRepository;
import com.university_java.service.StudentService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
    StudentService studentService;
	
	@Autowired
	StudentRepository studentRepository;


	@PostMapping("/create")
	public ResponseEntity<?> createStudent(@Valid @RequestBody StudentsModel student) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        // Create the student
	        studentService.createStudent(student);
	        response.put("data", student);
	        response.put("message", "Student created successfully.");
	        response.put("success", true);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "An error occurred: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateStudent(@RequestBody StudentsModel student) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        // Fetch the student by ID
	        Optional<StudentsModel> existingStudent = studentService.getStudentById(student.getId());
	        if (existingStudent.isPresent()) {
	       
	            student.setId(student.getId()); 
	            studentService.updateStudent(student);
	            response.put("data", existingStudent);
	            response.put("message", "Student updated successfully.");
	            response.put("success", true);
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("success", false);
	            response.put("message", "Student not found.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "An error occurred: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
    
    @GetMapping("/findAll")
    public ResponseEntity<?> getAllStudents(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size  
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            
        	Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        	Page<StudentsModel> studentsPage = studentService.getAllStudents(pageable);
        	
            response.put("success", true);
            response.put("message", "Students retrieved successfully.");
            response.put("data", studentsPage.getContent());
            response.put("currentPage", studentsPage.getNumber());
            response.put("totalItems", studentsPage.getTotalElements());
            response.put("totalPages", studentsPage.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/findStudent")
    public ResponseEntity<?> getStudentById(@RequestParam int id) {
        Map<String, Object> response = new HashMap<>();
        try {
           // String college = TenantContext.getCurrentTenant(); 

            Optional<StudentsModel> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                response.put("success", true);
                response.put("message", "Student retrieved successfully.");
                response.put("data", student);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Student not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PutMapping("/delete")
    public ResponseEntity<?> removeUser(@RequestParam int id) {
        Optional<StudentsModel> existStudent = studentRepository.findById(id);
     
        Map<String, String> response = new HashMap<>();

        if (existStudent.isPresent()) {
        	
        	StudentsModel student = existStudent.get();
        	
        	student.setActive(false);

            studentRepository.save(student);

            response.put("status", "success");
            response.put("message", "Student deleted successfully.");
            return ResponseEntity.ok(response); 
        } else {
           
            response.put("status", "error");
            response.put("message", "Student not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    
	}
    
    @GetMapping("/")
    public String home() {
    	return "Hi,Welcome";
    }
}
