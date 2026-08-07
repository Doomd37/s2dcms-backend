package com.myproject.S2dcms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.S2dcms.dto.auth.LoginRequest;
import com.myproject.S2dcms.model.Department;
import com.myproject.S2dcms.model.Role;
import com.myproject.S2dcms.model.Student;
import com.myproject.S2dcms.repository.DepartmentRepo;
import com.myproject.S2dcms.repository.RefreshTokenRepository;
import com.myproject.S2dcms.repository.StudentRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for AuthController
 * Tests the full HTTP request flow: Controller → Service → Database
 * Uses real database (H2 in-memory) and real Spring context
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Student testStudent;
    private Department testDepartment;

    @BeforeEach
    void setUp() {
        // Create test department first (student needs department_id)
        testDepartment = new Department();
        testDepartment.setEmail("test@dept.com");
        testDepartment.setPassword(passwordEncoder.encode("password123"));
        testDepartment.setDepartmentName("Test Department");
        testDepartment.setRole(Role.DEPARTMENT);
        testDepartment = departmentRepository.save(testDepartment);

        // Create test student with department_id
        testStudent = new Student();
        testStudent.setEmail("test@student.com");
        testStudent.setPassword(passwordEncoder.encode("password123"));
        testStudent.setName("Test Student");
        testStudent.setRegNo("TEST001");
        testStudent.setDepartment(testDepartment);
        testStudent.setRole(Role.STUDENT);
        testStudent.setEmailVerified(true);
        studentRepository.save(testStudent);
    }

    @AfterEach
    void tearDown() {
        // No manual cleanup needed - @DirtiesContext and H2 create-drop handle this
    }

    @Test
    void testLogin_Success_Student() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@student.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void testLogin_InvalidPassword() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@student.com");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogin_Success_Department() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@dept.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void testLogin_UserNotFound() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("nonexistent@test.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
