package com.project.back_end.controllers;

import com.project.back_end.dtos.UserDTO;
import com.project.back_end.models.User;
import com.project.back_end.responses.ResponseObject;
import com.project.back_end.services.users.IUserAccount;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserAccountController {

    private final IUserAccount userAccountService;

    @Autowired
    public UserAccountController(IUserAccount userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/test-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdminAccess() {
        return "Chỉ ADMIN mới thấy dòng này!";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable ObjectId id) throws Exception {
        return userAccountService.getUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerUser(@RequestBody UserDTO userAccountDTO) {
        try {
            User newUser = userAccountService.createAccount(userAccountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseObject.builder()
                    .data(newUser)
                    .message("User registered successfully")
                    .status(HttpStatus.CREATED.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @PostMapping("/login/phone")
    public ResponseEntity<ResponseObject> loginByPhoneNumber(@RequestParam String phonenumber,
            @RequestParam String password) {
        try {
            String token = userAccountService.loginByPhoneNumber(phonenumber, password);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(token)
                    .message("Login successful")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.UNAUTHORIZED.toString())
                    .build());
        }
    }

    @PostMapping("/login/email")
    public ResponseEntity<ResponseObject> loginByEmail(@RequestParam String email, @RequestParam String password) {
        try {
            String token = userAccountService.loginByEmail(email, password);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(token)
                    .message("Login successful")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.UNAUTHORIZED.toString())
                    .build());
        }
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> getUserAccountFromToken(@RequestParam String token) {
        try {
            User userAccount = userAccountService.getUserAccountFromToken(token);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(userAccount)
                    .message("User account retrieved successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.UNAUTHORIZED.toString())
                    .build());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateUserAccount(@PathVariable("id") ObjectId id,
            @RequestBody UserDTO userAccountDTO) {
        try {
            User updatedUser = userAccountService.updateUserAccount(id, userAccountDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(updatedUser)
                    .message("User account updated successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<ResponseObject> resetPassword(@PathVariable("id") ObjectId id,
            @RequestBody Map<String, String> request) {
        try {
            String newPassword = request.get("newPassword");
            if (newPassword == null) {
                throw new IllegalArgumentException("newPassword cannot be null");
            }
            userAccountService.resetPassword(id, newPassword);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Password reset successfully")
                    .data(userAccountService.getUserById(id))
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<ResponseObject> blockOrEnableAccount(@PathVariable("id") ObjectId id,
            @RequestParam boolean is_active) {
        try {
            userAccountService.blockOrEnbleAccount(id, is_active);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message(is_active ? "Account enabled successfully" : "Account blocked successfully")
                    .status(HttpStatus.OK.toString())
                    .data(userAccountService.getUserById(id))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> deleteUserAccount(@PathVariable("id") ObjectId id) {
        try {
            User userDelete = userAccountService.getUserById(id);
            userAccountService.deleteUserAccount(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("User account deleted successfully")
                    .data(userDelete)
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }
}
