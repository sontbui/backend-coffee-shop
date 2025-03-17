package com.project.back_end.responses.users;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    
    private ObjectId id;

    @Field("name") //
    private String fullName;

    @Field("phone")
    private String phoneNumber;

    @Field("email")
    private String email;

    private String address;

    private String password;

    private int point;

    private String role;

    @Field("is_active")
    private boolean isActive;

    @Field("createdAt")
    private String createdAt;
    @Field("updatedAt")
    private String updated_at;

    private String token;

    public static UserResponse fromUserAccount(UserResponse userAccount) {
        System.out.println("Mapping UserAccount to Response: " + userAccount);
        return UserResponse.builder()
                .id(userAccount.getId())
                .phoneNumber(userAccount.getPhoneNumber())
                .email(userAccount.getEmail())
                .password(userAccount.getPassword())
                .role(userAccount.getRole())
                .isActive(userAccount.isActive())
                .createdAt(userAccount.getCreatedAt())
                .updated_at(userAccount.getUpdated_at())
                .token(userAccount.getToken())
                .build();
    }
}
