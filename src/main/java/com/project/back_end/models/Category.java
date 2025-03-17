package com.project.back_end.models;

import java.util.Map;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "categories")
@Data
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    
    @org.springframework.data.annotation.Id
    @Field("_id")   
    private ObjectId id;

    @NotNull(message = "Category type is required")
    @Field("type")
    private String type;

    @NotNull(message = "Category type is required")
    @Field("name")
    private Map<String,String> name; 

    private String createdAt;

    private String updatedAt;
}
