package com.myproject.S2dcms.controller;

import com.myproject.S2dcms.Service.AIComplaintService;
import com.myproject.S2dcms.dto.ai.SummarizeRequest;
import com.myproject.S2dcms.dto.ai.SuggestReplyRequest;
import com.myproject.S2dcms.dto.ai.WriteComplaintRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private static final Logger logger = LoggerFactory.getLogger(AIController.class);
    private final AIComplaintService aiComplaintService;

    public AIController(AIComplaintService aiComplaintService) {
        this.aiComplaintService = aiComplaintService;
    }

    /**
     * Summarize a complaint
     */
    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeComplaint(@RequestBody SummarizeRequest request) {
        try {
            logger.info("Received summarize request with text length: {}", request.getText() != null ? request.getText().length() : 0);
            String summary = aiComplaintService.summarizeComplaint(request.getText());
            logger.info("Summarize request completed successfully");
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            logger.error("Error in summarize request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to generate summary. Please try again later.");
        }
    }

    /**
     * Suggest a reply for department staff
     */
    @PostMapping("/suggest-reply")
    public ResponseEntity<String> suggestReply(@RequestBody SuggestReplyRequest request) {
        try {
            logger.info("Received suggest-reply request with text length: {}", request.getComplaintText() != null ? request.getComplaintText().length() : 0);
            String reply = aiComplaintService.suggestReply(request.getComplaintText());
            logger.info("Suggest-reply request completed successfully");
            return ResponseEntity.ok(reply);
        } catch (Exception e) {
            logger.error("Error in suggest-reply request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to generate reply suggestion. Please try again later.");
        }
    }

    /**
     * Help a student write a complaint
     */
    @PostMapping("/write-complaint")
    public ResponseEntity<String> writeComplaint(@RequestBody WriteComplaintRequest request) {
        try {
            logger.info("Received write-complaint request with situation: {}", request.getSituation());
            String complaint = aiComplaintService.writeComplaint(request.getSituation());
            logger.info("Write-complaint request completed successfully");
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            logger.error("Error in write-complaint request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to generate complaint. Please try again later.");
        }
    }
}
