package com.project.back_end.responses.products;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DescriptionResponse {
    private String eng;
    private String vni;

    public static DescriptionResponse fromDescriptionDTO(Map<String, String> description) {
        return DescriptionResponse.builder()
                .eng(description.get("eng"))
                .vni(description.get("vni"))
                .build();
    }
}
