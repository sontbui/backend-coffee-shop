package com.project.back_end.models;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "movies")
public class Movie {
    

    @Id
    private ObjectId   id;

    private Map<String, String> title;
    
    @JsonProperty("genre")
    private List<String> genres;

    private int duration;

    private String posterUrl;

    private Map<String, String> description;

    private String releaseDate;

    private List<Character> characters;

    private String createdAt;
    private String updatedAt;

    
}
