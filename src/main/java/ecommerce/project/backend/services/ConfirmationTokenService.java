package ecommerce.project.backend.services;

import ecommerce.project.backend.entities.ConfirmationToken;
import ecommerce.project.backend.entities.User;

public interface ConfirmationTokenService {
    ConfirmationToken findByTokenValue(String token);
    void saveToken(ConfirmationToken confirmationToken);
    Boolean isUserHaveRegistrationToken(User user);
}
