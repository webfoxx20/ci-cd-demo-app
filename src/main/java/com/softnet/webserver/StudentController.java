package com.softnet.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentRepository repository;

    StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/students")
    ResponseEntity<StudentResponse> create(@RequestBody StudentRequest request) {
        log.info("POST /api/students - saving student with email={}", request.email());
        Student saved = repository.save(new Student(
                request.firstName(), request.lastName(),
                request.email(), request.enrollmentDate()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(StudentResponse.from(saved));
    }

    @GetMapping("/api/students/{id}")
    StudentResponse findById(@PathVariable Long id) {
        log.info("GET /api/students/{} - looking up student", id);
        return repository.findById(id)
                .map(StudentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id=" + id));
    }
}
