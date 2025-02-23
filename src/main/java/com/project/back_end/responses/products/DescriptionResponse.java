package com.project.back_end.responses.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.back_end.models.DescriptionProduct;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DescriptionResponse {
    private String en;
    private String vi;

    @JsonProperty("product_id")
    public static DescriptionResponse fromDescriptionDTO(DescriptionProduct description) {
        return DescriptionResponse.builder()
                .en(description.getEn())
                .vi(description.getVi())
                .build();
    }
}
