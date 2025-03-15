package com.project.back_end.responses.users;

import org.bson.types.ObjectId;
import org.hibernate.annotations.Fetch;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

    @Field("phone_number")
    private String phoneNumber;

    @Field("email")
    private String email;

    private String password;

    private String role;

    @Field("is_active")
    private boolean isActive;

    @Field("created_at")
    private String createdAt;
    @Field("updated_at")
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
