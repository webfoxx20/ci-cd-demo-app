package com.softnet.webserver;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    protected Student() {}

    Student(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
    }

    Long getId()                  { return id; }
    String getFirstName()         { return firstName; }
    String getLastName()          { return lastName; }
    String getEmail()             { return email; }
    LocalDate getEnrollmentDate() { return enrollmentDate; }
}
