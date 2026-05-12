package com.myproject.S2dcms.repository;

import com.myproject.S2dcms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findByEmailIgnoreCase(String email);

    Optional<Student> findByVerificationToken(String token);

    Optional<Student> findByPasswordResetToken(String token);
}
