package com.project.back_end.services.users;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.back_end.dtos.UserAccountDTO;
import com.project.back_end.models.UserAccount;
import com.project.back_end.responses.ResponseObject;

public interface IUserAccount {
    
    UserAccount createAccount(UserAccountDTO userAccountDTO) throws Exception;

    String loginByPhoneNumber(String phoneNumber, String password) throws Exception;

    String loginByEmail(String email, String password) throws Exception;

    UserAccount getUserAccountFromToken(String token) throws Exception;
    UserAccount getUserAccountFromRefreshToken(String token) throws Exception;

    UserAccount updateUserAccount(ObjectId id, UserAccountDTO userAccountDTO) throws Exception;

    void resetPassword(ObjectId id, String newPassword) throws Exception;

    UserAccount getUserById(ObjectId id) throws Exception;

    public void blockOrEnbleAccount(ObjectId id, boolean isActive) throws Exception;
    
    void deleteUserAccount(ObjectId id) throws Exception;
}