package com.softnet.webserver;

import java.time.LocalDate;

record StudentRequest(
        String firstName,
        String lastName,
        String email,
        LocalDate enrollmentDate
) {}
