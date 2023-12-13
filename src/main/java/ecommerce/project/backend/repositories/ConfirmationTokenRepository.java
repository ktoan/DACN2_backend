package ecommerce.project.backend.repositories;

import ecommerce.project.backend.entities.ConfirmationToken;
import ecommerce.project.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    List<ConfirmationToken> findByUserAndExpiredAtAfter(User user, Date date);
}
