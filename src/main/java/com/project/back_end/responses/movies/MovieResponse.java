package com.project.back_end.responses.movies;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.back_end.models.Character;
import com.project.back_end.models.Movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    private ObjectId id;
    private Map<String, String> title;
    private List<String> genre;
    private int duration;
    private String posterUrl;
    private Map<String, String> description;
    private String releaseDate;
    private List<Character> characters;
    private String createdAt;
    private String updatedAt;

    public static MovieResponse fromMovie(Movie movie) {
        return MovieResponse.builder()
            .id(movie.getId())
            .title(movie.getTitle())
            .genre(movie.getGenres())
            .duration(movie.getDuration())
            .posterUrl(movie.getPosterUrl())
            .description(movie.getDescription())
            .releaseDate(movie.getReleaseDate().toString())
            .characters(movie.getCharacters())
            .createdAt(movie.getCreatedAt().toString())
            .updatedAt(movie.getUpdatedAt().toString())
            .build();
    }
}
