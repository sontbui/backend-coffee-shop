package com.project.back_end.responses.products;

import lombok.*;

import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.back_end.models.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private ObjectId id;
    private String name;
    private String brandId;
    private String categoryId;
    private double price;
    private DescriptionResponse description;
    private List<String> imageUrls;
    private String createdAt;
    private String updatedAt;


    @JsonProperty("image_urls")
    public List<String> getImageUrls() {
        return imageUrls;
    }

    @JsonProperty("category_id")
    public static ProductResponse fromProduct(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brandId(product.getBrandId())
                .categoryId(product.getCategoryId())
                .price(product.getPrice())
                .description(DescriptionResponse.fromDescriptionDTO(product.getDescription()))
                .imageUrls(product.getImageUrls())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
