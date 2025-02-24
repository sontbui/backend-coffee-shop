package com.project.back_end.dtos;

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

@Document(collection = "user_accounts")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountDTO {
    
    @JsonProperty("_id")
    private ObjectId id;

    @Field("phone_number")
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    private String phoneNumber;

    @JsonProperty
    @Email(message = "Email should be valid")
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty 
    private String role;

    @JsonProperty
    @Field("is_active")
    private boolean isActive;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

}
