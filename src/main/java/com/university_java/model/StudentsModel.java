package com.university_java.model;

import java.util.Date; 

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Date of birth is required")
    @Column(name = "dob")
    private String dob;

    @NotBlank(message = "State is required")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "City is required")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "Degree is required")
    @Column(name = "degree")
    private String degree;
    
    @Column(name = "active")
    private Boolean active;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private Date createdTime;
	
	
	

}

