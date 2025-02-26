package com.project.back_end.services.users;

import com.project.back_end.dtos.UserAccountDTO;
import com.project.back_end.models.UserAccount;
import com.project.back_end.repositories.UserAccountRepository;
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

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder,
            JwtTokenUtil jwtTokenUtil) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserAccount createAccount(UserAccountDTO userAccountDTO) throws Exception {
        if (userAccountRepository.existsByEmail(userAccountDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }
        if (userAccountRepository.existsByPhoneNumber(userAccountDTO.getPhoneNumber())
                || userAccountDTO.getPhoneNumber() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with this phone number already exists" + userAccountDTO.getPhoneNumber());
        }
        UserAccount userAccount = UserAccount.builder()
                .email(userAccountDTO.getEmail())
                .phoneNumber(userAccountDTO.getPhoneNumber())
                .password(passwordEncoder.encode(userAccountDTO.getPassword()))
                .role(userAccountDTO.getRole())
                .createdAt(Instant.now().toString())
                .updatedAt(Instant.now().toString())
                .isActive(true)
                .build();
        return userAccountRepository.save(userAccount);
    }

    @Override
    public String loginByPhoneNumber(String phoneNumber, String password) throws Exception {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findByPhoneNumber(phoneNumber);
        if (userAccountOptional.isEmpty()
                || !passwordEncoder.matches(password, userAccountOptional.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number, password");
        }
        return jwtTokenUtil.generateToken(userAccountOptional.get());
    }

    @Override
    public String loginByEmail(String email, String password) throws Exception {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findByEmail(email);
        if (userAccountOptional.isEmpty()
                || !passwordEncoder.matches(password, userAccountOptional.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email, password");
        }
        return jwtTokenUtil.generateToken(userAccountOptional.get());
    }

    @Override
    public UserAccount getUserAccountFromToken(String token) throws Exception {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return userAccountRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public UserAccount getUserAccountFromRefreshToken(String token) throws Exception {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return userAccountRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public UserAccount updateUserAccount(ObjectId id, UserAccountDTO userAccountDTO) throws Exception {
        UserAccount existingUserAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
        existingUserAccount.setEmail(userAccountDTO.getEmail());
        existingUserAccount.setPhoneNumber(userAccountDTO.getPhoneNumber());
        existingUserAccount.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
        existingUserAccount.setRole(userAccountDTO.getRole());
        return userAccountRepository.save(existingUserAccount);
    }

    @Override
    public void resetPassword(ObjectId id, String newPassword) throws Exception {
        UserAccount existingUserAccount = userAccountRepository.findById(id.toString())
                .orElseThrow(() -> new Exception("User not found"));
        existingUserAccount.setPassword(passwordEncoder.encode(newPassword));
        userAccountRepository.save(existingUserAccount);
    }

    @Override
    public void blockOrEnbleAccount(ObjectId id, boolean isActive) throws Exception {
        UserAccount existingUserAccount = userAccountRepository.findById(id.toString())
                .orElseThrow(() -> new Exception("User not found"));
        existingUserAccount.setActive(isActive);
        userAccountRepository.save(existingUserAccount);
    }

    @Override
    public UserAccount getUserById(ObjectId id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public void deleteUserAccount(ObjectId id) throws Exception {
        UserAccount existingUserAccount = userAccountRepository.findById(id.toString())
                .orElseThrow(() -> new Exception("User not found"));
        userAccountRepository.delete(existingUserAccount);
    }
    
}
