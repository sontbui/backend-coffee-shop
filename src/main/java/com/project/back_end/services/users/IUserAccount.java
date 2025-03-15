package com.project.back_end.services.users;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.back_end.dtos.UserDTO;
import com.project.back_end.models.User;
import com.project.back_end.responses.ResponseObject;

public interface IUserAccount {
    
    User createAccount(UserDTO userAccountDTO) throws Exception;

    String loginByPhoneNumber(String phoneNumber, String password) throws Exception;

    String loginByEmail(String email, String password) throws Exception;

    User getUserAccountFromToken(String token) throws Exception;
    User getUserAccountFromRefreshToken(String token) throws Exception;

    User updateUserAccount(ObjectId id, UserDTO userAccountDTO) throws Exception;

    void resetPassword(ObjectId id, String newPassword) throws Exception;

    User getUserById(ObjectId id) throws Exception;

    public void blockOrEnbleAccount(ObjectId id, boolean isActive) throws Exception;
    
    void deleteUserAccount(ObjectId id) throws Exception;
}