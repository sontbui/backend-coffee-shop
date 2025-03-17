package com.project.back_end.services.users;

import com.project.back_end.dtos.UserDTO;
import com.project.back_end.models.User;
import com.project.back_end.repositories.UserRepository;
import com.project.back_end.responses.ResponseObject;
import com.project.back_end.utils.JwtTokenUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserAccountService implements IUserAccount {

    private final UserRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserAccountService(UserRepository userAccountRepository, PasswordEncoder passwordEncoder,
            JwtTokenUtil jwtTokenUtil) {
        this.userAccountRepository = userAccountRepository;
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
        User userAccount = User.builder()
                .fullName(userAccountDTO.getFullName())
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
