package com.softnet.webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTests {

    private MockMvc mockMvc;
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(StudentRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(repository)).build();
    }

    @Test
    void postStudentReturns201WithSavedRecord() throws Exception {
        Student saved = buildStudent(1L, "Jane", "Doe", "jane@example.com", LocalDate.of(2026, 6, 9));
        when(repository.save(any(Student.class))).thenReturn(saved);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Jane",
                                  "lastName": "Doe",
                                  "email": "jane@example.com",
                                  "enrollmentDate": "2026-06-09"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.enrollmentDate").value("2026-06-09"));
    }

    @Test
    void getStudentByIdReturns200WithRecord() throws Exception {
        Student student = buildStudent(1L, "Jane", "Doe", "jane@example.com", LocalDate.of(2026, 6, 9));
        when(repository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.enrollmentDate").value("2026-06-09"));
    }

    @Test
    void getStudentByIdReturns404WhenNotFound() throws Exception {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/99"))
                .andExpect(status().isNotFound());
    }

    // Reflectively sets the id field since the entity has no public setter
    private Student buildStudent(Long id, String firstName, String lastName, String email, LocalDate enrollmentDate) {
        try {
            Student s = new Student(firstName, lastName, email, enrollmentDate);
            var field = Student.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(s, id);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
