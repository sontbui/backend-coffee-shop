package com.project.back_end.models;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private ObjectId id;
    private String name;

    @Field("brand_id")
    private String brandId;

    @Field("category_id")
    private String categoryId;

    private double price;
    private Map<String, String> description;

    @Field("image_urls")
    private List<String> imageUrls;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;

    // Other fields and methods...
}
