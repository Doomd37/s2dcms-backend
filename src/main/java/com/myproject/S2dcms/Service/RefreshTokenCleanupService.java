package com.myproject.S2dcms.Service;

import com.myproject.S2dcms.model.RefreshToken;
import com.myproject.S2dcms.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RefreshTokenCleanupService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenCleanupService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Runs every 5hrs
    @Scheduled(cron = "0 0 */5 * * *")
    public void cleanupOldTokens() {

        System.out.println("Refresh token cleanup running...");

        refreshTokenRepository.deleteExpiredOrRevokedTokens();

        System.out.println("Cleanup completed");
        }
    }

