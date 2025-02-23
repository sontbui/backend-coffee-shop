package com.project.back_end.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.back_end.dtos.DescrioptionProductsDTO;

import jakarta.validation.constraints.Size;
import lombok.*;

@Document(collection = "products") 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {  
    
    @Id
    private ObjectId id;

    private String name; 

    private double price;

    @Size(min = 5, max = 200, message = "Image URLs must be between 5 and 200 characters")
    @JsonProperty("image_urls")
    private List<String> imageUrls; 

    @JsonProperty("brand_id")
    private String brandId; 

    @JsonProperty("category_id")
    private String categoryId; 

    private DescriptionProduct description;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("created_at")
    private String updatedAt;
}
