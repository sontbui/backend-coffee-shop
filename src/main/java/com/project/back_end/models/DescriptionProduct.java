package com.project.back_end.models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products") 
public class DescriptionProduct {

   @JsonProperty("description.vni")
    private String description_vni;

    @JsonProperty("description.eng")
    private String description_eng;
}
