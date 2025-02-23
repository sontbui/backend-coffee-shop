package com.project.back_end.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsDTO {

    @JsonProperty("_id")
    private ObjectId id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String name; 

    @JsonProperty("brand_id")
    private String brandId;

    @Field("category_id")
    @JsonProperty("category_id")
    private String categoryId;

    @Min(value = 1, message = "Price must be greater than 0")
    @Max(value = 1000000, message = "Price must be less than 1000000")
    private double price;

    @Field("description")
    private Map<String, String> description;

    @Size(min = 2, max = 255, message = "Each image URL must be between 2 and 255 characters")
    @Field("image_urls")
    private List<String> imageUrls;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
