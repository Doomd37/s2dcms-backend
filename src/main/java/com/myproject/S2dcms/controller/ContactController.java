package com.myproject.S2dcms.controller;

import com.myproject.S2dcms.Service.EmailProducerService;
import com.myproject.S2dcms.Service.UserActionService;
import com.myproject.S2dcms.dto.email.EmailMessage;
import com.myproject.S2dcms.dto.message.ContactRequest;
import com.myproject.S2dcms.model.ContactMessage;
import com.myproject.S2dcms.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageRepository repository;
    private final EmailProducerService emailProducerService;
    private final UserActionService userActionService;

    @Value("${admin.email}")
    private String adminEmail;

    public ContactController(ContactMessageRepository repository, EmailProducerService emailProducerService, UserActionService userActionService) {
        this.repository = repository;
        this.emailProducerService = emailProducerService;
        this.userActionService = userActionService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody ContactRequest request) {
        String email = request.getEmail();
        String actionType = "CONTACT_FORM";

        userActionService.checkRateLimit(email, actionType);

        ContactMessage msg = new ContactMessage();
        msg.setName(request.getName());
        msg.setEmail(request.getEmail());
        msg.setMessage(request.getMessage());

        repository.save(msg);

        EmailMessage emailMessage = new EmailMessage(
            adminEmail,
            "New Contact Form Submission from " + request.getName(),
            "CONTACT",
            null,
            request.getName(),
            request.getMessage(),
            request.getEmail()
        );

        emailProducerService.sendEmailMessage(emailMessage);

        return ResponseEntity.ok("Message sent successfully");
    }
}
