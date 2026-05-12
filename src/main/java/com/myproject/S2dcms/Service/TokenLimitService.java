package com.myproject.S2dcms.Service;

import com.myproject.S2dcms.model.Department;
import com.myproject.S2dcms.model.RefreshToken;
import com.myproject.S2dcms.model.Student;
import com.myproject.S2dcms.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TokenLimitService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService tokenService;

    public TokenLimitService(RefreshTokenRepository refreshTokenRepository, RefreshTokenService tokenService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenService = tokenService;
    }

    public void manageTokenLimitForStudent(Student student) {

        List<RefreshToken> tokens = refreshTokenRepository.findByStudent(student);

        tokens.sort(Comparator.comparing(RefreshToken::getCreatedAt));

        while (tokens.size() >= 5) {
            RefreshToken oldest = tokens.remove(0);
            tokenService.revokeToken(oldest.getToken());
        }
    }

    public void manageTokenLimitForDepartment(Department department) {

        List<RefreshToken> tokens = refreshTokenRepository.findByDepartment(department);

        tokens.sort(Comparator.comparing(RefreshToken::getCreatedAt));

        while (tokens.size() >= 5) {
            RefreshToken oldest = tokens.remove(0);
            tokenService.revokeToken(oldest.getToken());
        }
    }


}
