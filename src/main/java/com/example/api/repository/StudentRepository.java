package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
