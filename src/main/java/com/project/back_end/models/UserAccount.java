package com.project.back_end.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserAccount {
    
    @Id
    private ObjectId id;

    @JsonProperty("phone_number")   
    @Field("phone_number")
    private String phoneNumber;
;

    private String email;

    @Field("password")
    private String password;

    @Field("role")
    private String role;

    @Field("is_active")
    private boolean isActive;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;


}
