package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.dto.UserDTO;
import ecommerce.project.backend.entities.ConfirmationToken;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.enums.Role;
import ecommerce.project.backend.exceptions.*;
import ecommerce.project.backend.mappers.UserMapper;
import ecommerce.project.backend.repositories.UserRepository;
import ecommerce.project.backend.requests.ChangePasswordRequest;
import ecommerce.project.backend.requests.LoginRequest;
import ecommerce.project.backend.requests.UpdateUserRequest;
import ecommerce.project.backend.services.ConfirmationTokenService;
import ecommerce.project.backend.services.UserService;
import ecommerce.project.backend.utils.context.ContextService;
import ecommerce.project.backend.utils.download.DownloadUtils;
import ecommerce.project.backend.utils.email.EmailBuilder;
import ecommerce.project.backend.utils.email.EmailService;
import ecommerce.project.backend.utils.upload.file.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static ecommerce.project.backend.constants.Messaging.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Value("${user.avatar.default}")
    private String avatarDefault;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final EmailBuilder emailBuilder;
    private final ContextService contextService;
    private final FileService fileService;
    private final DownloadUtils downloadUtils;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserDTO> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO verifyUser(MultipartFile file) {
        User user = contextService.loadUserFromContext();

        if (!StringUtils.isEmpty(user.getVerifiedImage())) {
            throw new BadRequestException("User has already verified!");
        }

        String url = fileService.uploadImage(file);
        user.setVerifiedImage(url);

        System.out.println(url);

//        User verifiedUser = saveUser(user);

        return null;
    }

    @Override
    public UserDTO loadUser() {
        User user = contextService.loadUserFromContext();
        return userMapper.toDTO(user);
    }

    @Override
    public void createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException(String.format(EMAIL_ALREADY_TAKEN_MSG, userDTO.getEmail()));
        }
        User user = userMapper.toEntity(userDTO);
        user.setAvatar(avatarDefault);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(false);
        user.setLocked(false);
        saveUser(user);
    }

    @Override
    public String uploadAvatar(Long userId, MultipartFile multipartFile) {
        User user = findUserById(userId);
        if (!Objects.equals(user.getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        String newAvatarURL = fileService.uploadImage(multipartFile);
        user.setAvatar(newAvatarURL);
        user = saveUser(user);
        return user.getAvatar();
    }

    @Override
    public LoginRequest validateLoginRequest(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            throw new BadRequestException("Username/Password is invalid!");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new BadRequestException("Username/Password is invalid!");
        }
        if (!user.get().getEnabled()) {
            throw new NotEnableException();
        }
        return loginRequest;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userMapper.toDTO(userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_EMAIL_MSG, email))));
    }

    @Override
    public UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User user = findUserById(userId);
        if (!updateUserRequest.getEmail().equals(user.getEmail())) {
            throw new BadRequestException("Email isn't editable!");
        }
        if (!Objects.equals(user.getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        user.setFullName(updateUserRequest.getFullName());
        user.setPhone(updateUserRequest.getPhone());
        user.setDayOfBirth(updateUserRequest.getDayOfBirth());
        user.setGender(updateUserRequest.getGender());
        user = saveUser(user);
        return userMapper.toDTO(user);
    }

    @Override
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByTokenValue(token);
        if (confirmationToken.getUser().getEnabled()) {
            throw new ServerErrorException("User have already confirmed!");
        }
        Date currentTime = new Date();
        if (!currentTime.before(confirmationToken.getExpiredAt())) {
            throw new ServerErrorException("Token has expired!");
        }
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        saveUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Username not found!");
        }
    }

    @Override
    public void resendConfirmToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_EMAIL_MSG, email)));
        if (user.getEnabled()) {
            throw new NotAccessException("User is enabled! Then you can try login process.");
        }
        if (confirmationTokenService.isUserHaveRegistrationToken(user)) {
            throw new NotAccessException("User already have token! Try check your email.");
        }
        String confirmationToken = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(confirmationToken, new Date(System.currentTimeMillis() + 15 * 60 * 1000), user);
        confirmationTokenService.saveToken(token);
        emailService.send("Confirm your registration", email, emailBuilder.buildConfirmationEmail(user.getFullName(), confirmationToken));
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        User user = findUserById(userId);
        if (!Objects.equals(user.getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Your password is incorrect!");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        saveUser(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_ID_MSG, userId)));
    }
}
