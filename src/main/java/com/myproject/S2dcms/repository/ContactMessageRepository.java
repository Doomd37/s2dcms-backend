package com.myproject.S2dcms.repository;

import com.myproject.S2dcms.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
