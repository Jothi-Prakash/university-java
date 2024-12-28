package com.abc_university.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc_university.confiq.TenantContext;
import com.abc_university.model.StudentsModel;
import com.abc_university.repository.StudentRepository;
import com.abc_university.service.StudentService;



@RestController
@RequestMapping("/api/students")
public class StudentController {

	@Autowired
    StudentService studentService;


    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(@RequestBody StudentsModel student) {
        Map<String, Object> response = new HashMap<>();
        try {
            String college = TenantContext.getCurrentTenant(); // Retrieve tenant from context

            // Check if student exists and update or create
            Optional<StudentsModel> existingStudent = studentService.getStudentById(student.getId());
            if (existingStudent.isPresent()) {
                studentService.updateStudent(student);
                response.put("message", "Student updated successfully.");
            } else {
                studentService.createStudent(student);
                response.put("message", "Student created successfully.");
            }

            response.put("success", true);
            response.put("data", student);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents() {
        Map<String, Object> response = new HashMap<>();
        try {
            String college = TenantContext.getCurrentTenant(); // Retrieve tenant from context

            // Fetch all students for the college
            List<StudentsModel> students = studentService.getAllStudents();
            response.put("success", true);
            response.put("message", "Students retrieved successfully.");
            response.put("data", students);
            return ResponseEntity.ok(response);
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
            String college = TenantContext.getCurrentTenant(); 

            // Fetch the student by ID for the college
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
}
