package com.myproject.S2dcms.controller;

import com.myproject.S2dcms.Service.DepartmentService;
import com.myproject.S2dcms.dto.auth.ChangePasswordRequest;
import com.myproject.S2dcms.dto.department.DepartmentResponse;
import com.myproject.S2dcms.dto.message.MessagePreviewDto;
import com.myproject.S2dcms.dto.message.MessageResponse;
import com.myproject.S2dcms.dto.message.ReplyMessageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    //  GET PROFILE
    @GetMapping("/profile")
    public ResponseEntity<DepartmentResponse> getProfile(Principal principal) {
        return ResponseEntity.ok(departmentService.getProfile(principal.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<DepartmentResponse> updateProfile(
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(departmentService.updateProfile(email, image));
    }

    //  GET COMPLAINTS / MESSAGE HISTORY
    @GetMapping("/complaints")
    public ResponseEntity<Page<MessagePreviewDto>> getComplaints(
            @RequestParam(defaultValue = "ALL") String status,
            @RequestParam(defaultValue = "NEWEST") String sort,
            Pageable pageable,
            Principal principal) {

        Page<MessagePreviewDto> page = departmentService.getComplaints(
                principal.getName(),
                status,
                sort,
                pageable
        );

        return ResponseEntity.ok(page);
    }


    @GetMapping("/complaints/{id}")
    public ResponseEntity<MessageResponse> openMessage(@PathVariable Long id,Principal principal) {

        MessageResponse response = departmentService.openMessage(id, principal.getName());

        return ResponseEntity.ok(response);
    }

    // ==================== REPLY TO COMPLAINT ====================
    @PostMapping("/reply")
    public ResponseEntity<MessageResponse> replyToComplaint(
            @ModelAttribute ReplyMessageRequest request,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment,
            Principal principal
    ) throws IOException {
        MessageResponse response = departmentService.replyToComplaint(
                request,attachment ,principal.getName());
        return ResponseEntity.ok(response);
    }


    // ==================== CLOSE COMPLAINT ====================
    @PutMapping("/complaints/{complaintId}/close")
    public ResponseEntity<MessageResponse> closeComplaint(
            @PathVariable Long complaintId,
            Principal principal
    ) {
        // Calls service to set status = CLOSED
        return ResponseEntity.ok(departmentService.closeComplaint(complaintId, principal.getName()));
    }


}
