package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.UserDTO;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.requests.ChangePasswordRequest;
import ecommerce.project.backend.requests.LoginRequest;
import ecommerce.project.backend.requests.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    UserDTO loadUser();
    UserDTO getUserByEmail(String email);
    UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest);
    void createUser(UserDTO userDTO);
    String uploadAvatar(Long userId, MultipartFile multipartFile);
    LoginRequest validateLoginRequest(LoginRequest loginRequest);
    void confirmToken(String token);
    void resendConfirmToken(String email);
    void changePassword(Long userId, ChangePasswordRequest changePasswordRequest);
    void deleteUser(Long userId);
    List<UserDTO> fetchAllUsers();
    UserDTO verifyUser(MultipartFile file);
}
