package com.bada.badaback.auth.service;

import com.bada.badaback.auth.domain.Token;
import com.bada.badaback.auth.domain.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public void synchronizeRefreshToken(Long memberId, String refreshToken) {
        tokenRepository.findByMemberId(memberId)
                .ifPresentOrElse(
                        token -> token.updateRefreshToken(refreshToken),
                        () -> tokenRepository.save(Token.createToken(memberId, refreshToken))
                );
    }

    @Transactional
    public void reissueRefreshTokenByRtrPolicy(Long memberId, String newRefreshToken) {
        tokenRepository.reissueRefreshTokenByRtrPolicy(memberId, newRefreshToken);
    }

    @Transactional
    public void deleteRefreshTokenByMemberId(Long memberId) {
        tokenRepository.deleteByMemberId(memberId);
    }

    public boolean isRefreshTokenExists(Long memberId, String refreshToken) {
        return tokenRepository.existsByMemberIdAndRefreshToken(memberId, refreshToken);
    }
}

