package com.softnet.webserver;

import java.time.LocalDate;

record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate enrollmentDate
) {
    static StudentResponse from(Student s) {
        return new StudentResponse(
                s.getId(), s.getFirstName(), s.getLastName(),
                s.getEmail(), s.getEnrollmentDate()
        );
    }
}
