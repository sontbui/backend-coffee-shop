package com.project.back_end.responses.movies;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private static final String DEFAULT_POSTER = "https://res.cloudinary.com/dzw7jd4hi/image/upload/v1741692320/movie_images/ujsqtq50etoyvxttyv7f.jpg";
    @JsonProperty("_id")
    @JsonSerialize
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
                .posterUrl(movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty() ? movie.getPosterUrl()
                        : DEFAULT_POSTER)
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate().toString())
                .characters(movie.getCharacters())
                .createdAt(movie.getCreatedAt().toString())
                .updatedAt(movie.getUpdatedAt().toString())
                .build();
    }
}
