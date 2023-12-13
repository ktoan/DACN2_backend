package ecommerce.project.backend.utils.email;

public interface EmailService {
    void send(String subject, String to, String email);
}
