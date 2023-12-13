package ecommerce.project.backend.exceptions;

public class NotEnableException extends RuntimeException {
    public NotEnableException() {
        super("User is not enabled. Please confirm your account!");
    }
}
