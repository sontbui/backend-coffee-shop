package com.project.back_end.dtos;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "movies")
public class MovieDTO {
    

    @JsonProperty("_id")
    private ObjectId    id;

    @JsonProperty("title")
    private Map<String, String> title;

    @JsonProperty("genre")
    private List<String> genre;

    private int duration;

    private String posterUrl;

    private Map<String, String> description;

    private String releaseDate;

    private List<Character> characters;

    private String createdAt;
    private String updatedAt;
}
