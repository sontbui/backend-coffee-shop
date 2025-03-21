package com.project.back_end.dtos;

import java.io.File;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    
    @JsonProperty("_id")
    private ObjectId id;

    @Field("name")
    private String fullName;

    private String gender;

    private String dob;

    @Field("phone")
    @JsonProperty("phone")
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

    private String address;

    private String password;

    private String point;

    private String role;

    private String avatar;

    @Field("is_active")
    private boolean isActive;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

}
