package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.entities.ConfirmationToken;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.exceptions.NotFoundException;
import ecommerce.project.backend.repositories.ConfirmationTokenRepository;
import ecommerce.project.backend.repositories.UserRepository;
import ecommerce.project.backend.services.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static ecommerce.project.backend.constants.Messaging.TOKEN_NOT_FOUND_VALUE_MSG;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public ConfirmationToken findByTokenValue(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundException(String.format(TOKEN_NOT_FOUND_VALUE_MSG, token)));
    }

    @Override
    public void saveToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Boolean isUserHaveRegistrationToken(User user) {
        List<ConfirmationToken> confirmationTokens = confirmationTokenRepository.findByUserAndExpiredAtAfter(user, new Date());
        return confirmationTokens.size() > 0;
    }
}
