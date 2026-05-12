package com.myproject.S2dcms.repository;


import com.myproject.S2dcms.model.UserActionLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserActionLimitRepository extends JpaRepository<UserActionLimit, Long> {
    Optional<UserActionLimit> findByEmailAndAction(String email, String action);
}
