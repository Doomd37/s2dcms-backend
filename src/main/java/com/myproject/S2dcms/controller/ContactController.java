package com.myproject.S2dcms.controller;

import com.myproject.S2dcms.dto.message.ContactRequest;
import com.myproject.S2dcms.model.ContactMessage;
import com.myproject.S2dcms.repository.ContactMessageRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageRepository repository;

    public ContactController(ContactMessageRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody ContactRequest request) {

        ContactMessage msg = new ContactMessage();
        msg.setName(request.getName());
        msg.setEmail(request.getEmail());
        msg.setMessage(request.getMessage());

        repository.save(msg);

        return ResponseEntity.ok("Message sent successfully");
    }
}
