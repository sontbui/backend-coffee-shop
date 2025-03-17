package com.project.back_end.dtos;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "categories")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryDTO {
    
    @JsonProperty("_id")
    private ObjectId id;

    @NotNull(message = "Category type is required")
    private String type;

    @NotNull(message = "Category name is required")
    private Map<String,String> name;

    private String createdAt;

    private String updatedAt;
}
