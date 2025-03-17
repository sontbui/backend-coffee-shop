package com.project.back_end.responses.categories;

import java.util.Map;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    @JsonProperty("_id")
    private ObjectId id;

    private String type;

    private Map<String,String> name;
}