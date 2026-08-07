package com.myproject.S2dcms.Service;

import com.myproject.S2dcms.model.Department;
import com.myproject.S2dcms.model.Role;
import com.myproject.S2dcms.model.Student;
import com.myproject.S2dcms.repository.DepartmentRepo;
import com.myproject.S2dcms.repository.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLookupService {

    private final StudentRepo studentRepository;
    private final DepartmentRepo departmentRepository;

    public UserLookupService(StudentRepo studentRepository, DepartmentRepo departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    public UserResult findByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmailIgnoreCase(email);
        if (student.isPresent()) {
            return new UserResult(student.get(), UserType.STUDENT);
        }

        Optional<Department> department = departmentRepository.findByEmailIgnoreCase(email);
        if (department.isPresent()) {
            return new UserResult(department.get(), UserType.DEPARTMENT);
        }

        return null;
    }

    public UserResult findByPasswordResetToken(String token) {
        Optional<Student> student = studentRepository.findByPasswordResetToken(token);
        if (student.isPresent()) {
            return new UserResult(student.get(), UserType.STUDENT);
        }

        Optional<Department> department = departmentRepository.findByPasswordResetToken(token);
        if (department.isPresent()) {
            return new UserResult(department.get(), UserType.DEPARTMENT);
        }

        return null;
    }

    public record UserResult(Object user, UserType userType) {
        public Student getStudent() {
            if (userType != UserType.STUDENT) {
                throw new IllegalStateException("User is not a student");
            }
            return (Student) user;
        }

        public Department getDepartment() {
            if (userType != UserType.DEPARTMENT) {
                throw new IllegalStateException("User is not a department");
            }
            return (Department) user;
        }

        public String getEmail() {
            return userType == UserType.STUDENT ? getStudent().getEmail() : getDepartment().getEmail();
        }

        public String getPassword() {
            return userType == UserType.STUDENT ? getStudent().getPassword() : getDepartment().getPassword();
        }

        public Role getRole() {
            return userType == UserType.STUDENT ? getStudent().getRole() : getDepartment().getRole();
        }
    }

    public enum UserType {
        STUDENT, DEPARTMENT
    }
}
