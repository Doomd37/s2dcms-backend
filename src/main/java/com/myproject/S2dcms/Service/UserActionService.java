package com.myproject.S2dcms.Service;

import com.myproject.S2dcms.Exception.RateLimitException;
import com.myproject.S2dcms.model.UserActionLimit;
import com.myproject.S2dcms.repository.UserActionLimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserActionService {

    private final UserActionLimitRepository limitRepository;
    @Value("${limit.max_attempt}")
    private int MAX_ATTEMPTS; // e.g., max 3 per hour
    @Value("${limit.cooldown_minutes}")
    private int COOLDOWN_MINUTES;     // reset after 60 min

    public UserActionService(UserActionLimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    public void checkRateLimit(String email, String action) {

        UserActionLimit limit = limitRepository
                .findByEmailAndAction(email, action)
                .orElse(new UserActionLimit());

        LocalDateTime now = LocalDateTime.now();

        if (limit.getId() == null) {
            limit.setEmail(email);
            limit.setAction(action);
            limit.setCount(1);
            limit.setLastRequest(now);
            limitRepository.save(limit);
            return;
        }

        //  FIRST check cooldown reset AFTER blocking logic
        if (limit.getCount() > MAX_ATTEMPTS) {
            if (limit.getLastRequest().plusMinutes(COOLDOWN_MINUTES).isAfter(now)) {
                throw new RateLimitException("Too many attempts. Please try again later.");
            } else {
                // cooldown passed → reset
                limit.setCount(1);
                limit.setLastRequest(now);
                limitRepository.save(limit);
                return;
            }
        }

        // Increment count
        limit.setCount(limit.getCount() + 1);
        limit.setLastRequest(now);
        limitRepository.save(limit);
    }
}