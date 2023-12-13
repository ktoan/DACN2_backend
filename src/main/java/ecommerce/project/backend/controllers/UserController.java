package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.UserDTO;
import ecommerce.project.backend.requests.ChangePasswordRequest;
import ecommerce.project.backend.requests.UpdateUserRequest;
import ecommerce.project.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    @Operation(summary = "Fetch all users")
    public ResponseEntity<Object> fetchAllUsers() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("users", userService.fetchAllUsers());
        return ResponseEntity.ok(resp);
    }


    @PostMapping(value = "/change-avatar/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Change user's avatar")
    public ResponseEntity<Object> changeAvatar(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        String newAvatar = userService.uploadAvatar(userId, file);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newAvatarURL", newAvatar);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/update/{userId}")
    @Operation(summary = "Update user's information")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        UserDTO updatedUser = userService.updateUser(userId, updateUserRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("updatedUser", updatedUser);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get user by their own email")
    public ResponseEntity<Object> updateUser(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("user", user);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/change-password/{userId}")
    @Operation(summary = "Change user's password")
    public ResponseEntity<Object> changePassword(@PathVariable Long userId, @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(userId, changePasswordRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Change password successfully!");
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "Delete user by their own id")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Delete user successfully!");
        return ResponseEntity.ok(resp);
    }
}
