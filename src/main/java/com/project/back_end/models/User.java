package com.project.back_end.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @org.springframework.data.annotation.Id
    @Field("_id")
    private ObjectId id;

    @Field("name")
    private String fullName;

    private String gender;

    private String dob;

    @Field("phone")
    private String phoneNumber;

    @Field("email")
    private String email;

    private String address;

    @Field("password")
    private String password;

    private int point;

    private String role;

    @Field("createdAt")
    private String createdAt;

    @Field("updatedAt")
    private String updatedAt;

    @Field("is_active")
    private boolean isActive;

    @Field("avatar")
    private String avatar;
}
