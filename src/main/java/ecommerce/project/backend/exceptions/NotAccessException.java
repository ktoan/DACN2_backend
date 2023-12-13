package ecommerce.project.backend.exceptions;

public class NotAccessException extends RuntimeException {
    public NotAccessException() {
        super("User isn't allowed to try this process!");
    }

    public NotAccessException(String message) {
        super(message);
    }
}
