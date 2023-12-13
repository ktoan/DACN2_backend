package ecommerce.project.backend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, Object> resp = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        resp.put("success", false);
        resp.put("timestamp", LocalDateTime.now().toString());
        resp.put("errors", errors);
        return ResponseEntity.status(400).body(resp);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> bind(BindException e) {
        Map<String, Object> resp = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        resp.put("success", false);
        resp.put("timestamp", LocalDateTime.now().toString());
        resp.put("errors", errors);
        return ResponseEntity.status(400).body(resp);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequest(BadRequestException e) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", false);
        resp.put("timestamp", LocalDateTime.now().toString());
        resp.put("msg", e.getMessage());
        return ResponseEntity.status(400).body(resp);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Object> serverError(ServerErrorException e) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", false);
        resp.put("timestamp", LocalDateTime.now().toString());
        resp.put("msg", e.getMessage());
        return ResponseEntity.status(500).body(resp);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFound(NotFoundException e) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", false);
        resp.put("timestamp", LocalDateTime.now().toString());
        resp.put("msg", e.getMessage());
        return ResponseEntity.status(404).body(resp);
    }

    @ExceptionHandler(NotAccessException.class)
    public ResponseEntity<Object> notAccess(NotAccessException e) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", false);
        resp.put("timestamp", LocalDateTime.now().toString());
        resp.put("msg", e.getMessage());
        return ResponseEntity.status(403).body(resp);
    }

    @ExceptionHandler(NotEnableException.class)
    public ResponseEntity<Object> notEnable(NotEnableException e) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", false);
        resp.put("timestamp", LocalDateTime.now().toString());
        resp.put("msg", e.getMessage());
        return ResponseEntity.status(401).body(resp);
    }
}
