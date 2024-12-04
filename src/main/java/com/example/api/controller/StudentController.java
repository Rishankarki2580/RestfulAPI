package com.example.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.api.exception.StudentNotFoundException;
import com.example.api.model.Student;
import com.example.api.repository.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/students")
	public List<Student> retrieveAllStudents(){
		return studentRepository.findAll();
	}
	
	@GetMapping("/students/{id}")
	public Student retrieveStudent(@PathVariable long id) {
		Optional<Student> student = studentRepository.findById(id);
		
		if(student.isEmpty())
			throw new StudentNotFoundException("id-" + id);
		
		return student.get();
	}
	

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentRepository.deleteById(id);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();

    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable long id) {

        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isEmpty())
            return ResponseEntity.notFound().build();

        student.setId(id);

        studentRepository.save(student);

        return ResponseEntity.noContent()
                .build();
    }

}
