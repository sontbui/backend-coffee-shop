package com.project.back_end.services.users;

import com.cloudinary.Cloudinary;
import com.project.back_end.dtos.UserDTO;
import com.project.back_end.models.User;
import com.project.back_end.repositories.CloudianryRepository;
import com.project.back_end.repositories.UserRepository;
import com.project.back_end.services.cloudinary.CloudinaryService;
import com.project.back_end.utils.JwtTokenUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserAccountService implements IUserAccount {

    private final String folderAvatar = "users";
    private final String URL_AVATAR_DEFAULT_MALE = "https://res.cloudinary.com/dzw7jd4hi/image/upload/v1742306030/users/avatarDefaultMale.jpg";
    private final String URL_AVATAR_DEFAULT_FEMALE = "https://res.cloudinary.com/dzw7jd4hi/image/upload/v1742306030/users/avatarDefaultFemale.jpg";
    private final String URL_AVATAR_DEFAULT_LGBT = "https://res.cloudinary.com/dzw7jd4hi/image/upload/v1742306492/users/avatarDefaultLGBT.jpg";

    private final CloudianryRepository cloudianryRepository;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserAccountService(UserRepository userAccountRepository, CloudinaryService cloudinaryService,
            PasswordEncoder passwordEncoder,
            JwtTokenUtil jwtTokenUtil, CloudianryRepository cloudianryRepository) {
        this.cloudianryRepository = cloudianryRepository;
        this.userAccountRepository = userAccountRepository;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public User createAccount(UserDTO userAccountDTO) throws Exception {
        if (userAccountRepository.existsByEmail(userAccountDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }
        if (userAccountRepository.existsByPhoneNumber(userAccountDTO.getPhoneNumber())
                || userAccountDTO.getPhoneNumber() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with this phone number already exists" + userAccountDTO.getPhoneNumber());
        }
        if (userAccountDTO.getAvatar() == null ||
                userAccountDTO.getAvatar().isEmpty() ||
                userAccountDTO.getAvatar().isBlank() ||
                (userAccountDTO.getAvatar()).toLowerCase().equals("null")) {

            if (userAccountDTO.getGender().equals("male")) {
                userAccountDTO.setAvatar(URL_AVATAR_DEFAULT_MALE);
            } else if (userAccountDTO.getGender().equals("female")) {
                userAccountDTO.setAvatar(URL_AVATAR_DEFAULT_FEMALE);
            } else {
                userAccountDTO.setAvatar(URL_AVATAR_DEFAULT_LGBT);
            }
        }
        User userAccount = User.builder()
                .fullName(userAccountDTO.getFullName())
                .gender(userAccountDTO.getGender())
                .dob(userAccountDTO.getDob())
                .avatar(userAccountDTO.getAvatar())
                .email(userAccountDTO.getEmail())
                .phoneNumber(userAccountDTO.getPhoneNumber())
                .address(userAccountDTO.getAddress())
                .password(passwordEncoder.encode(userAccountDTO.getPassword()))
                .point(0)
                .role(userAccountDTO.getRole())
                .createdAt(Instant.now().toString())
                .updatedAt(Instant.now().toString())
                .isActive(true)
                .build();
        return userAccountRepository.save(userAccount);
    }

    @Override
    public void uploadAvatar(ObjectId id, String avatar) throws Exception {
        if (!cloudianryRepository.isImage(avatar)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image! Available for png | jpg | jpeg");
        }
        User existingUserAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found."));
        existingUserAccount.setAvatar(avatar);
        userAccountRepository.save(existingUserAccount);
    }

    @Override
    public String loginByPhoneNumber(String phoneNumber, String password) throws Exception {
        Optional<User> userAccountOptional = userAccountRepository.findByPhoneNumber(phoneNumber);
        if (userAccountOptional.isEmpty()
                || !passwordEncoder.matches(password, userAccountOptional.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number, password");
        }
        return jwtTokenUtil.generateToken(userAccountOptional.get());
    }

    @Override
    public String loginByEmail(String email, String password) throws Exception {
        Optional<User> userAccountOptional = userAccountRepository.findByEmail(email);
        if (userAccountOptional.isEmpty()
                || !passwordEncoder.matches(password, userAccountOptional.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email, password");
        }
        return jwtTokenUtil.generateToken(userAccountOptional.get());
    }

    @Override
    public User getUserAccountFromToken(String token) throws Exception {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return userAccountRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User getUserAccountFromRefreshToken(String token) throws Exception {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return userAccountRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User updateUserAccount(ObjectId id, UserDTO userAccountDTO) throws Exception {
        User existingUserAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
        existingUserAccount.setFullName(userAccountDTO.getFullName());
        existingUserAccount.setGender(userAccountDTO.getGender());
        existingUserAccount.setDob(userAccountDTO.getDob());
        existingUserAccount.setAddress(userAccountDTO.getAddress());
        existingUserAccount.setEmail(userAccountDTO.getEmail());
        existingUserAccount.setPhoneNumber(userAccountDTO.getPhoneNumber());
        existingUserAccount.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
        existingUserAccount.setRole(userAccountDTO.getRole());
        existingUserAccount.setActive(userAccountDTO.isActive());
        existingUserAccount.setCreatedAt(existingUserAccount.getCreatedAt());
        existingUserAccount.setPoint(existingUserAccount.getPoint());
        existingUserAccount.setUpdatedAt(Instant.now().toString());
        return userAccountRepository.save(existingUserAccount);
    }

    @Override
    public void resetPassword(ObjectId id, String newPassword) throws Exception {
        User existingUserAccount = userAccountRepository.findById(id.toString())
                .orElseThrow(() -> new Exception("User not found"));
        System.out.println("Existing User: " + existingUserAccount);
        existingUserAccount.setPassword(passwordEncoder.encode(newPassword));
        existingUserAccount.setUpdatedAt(Instant.now().toString());
        userAccountRepository.save(existingUserAccount);
    }

    @Override
    public void blockOrEnbleAccount(ObjectId id, boolean isActive) throws Exception {
        User existingUserAccount = userAccountRepository.findById(id.toString())
                .orElseThrow(() -> new Exception("User not found"));
        existingUserAccount.setActive(isActive);
        userAccountRepository.save(existingUserAccount);
    }

    @Override
    public User getUserById(ObjectId id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public void deleteUserAccount(ObjectId id) throws Exception {
        User existingUserAccount = userAccountRepository.findById(id.toString())
                .orElseThrow(() -> new Exception("User not found"));
        userAccountRepository.delete(existingUserAccount);
    }

}
