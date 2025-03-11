package com.project.back_end.services.movies;

import com.project.back_end.models.Movie;
import org.bson.types.ObjectId;

import java.util.List;

public interface IMovie {
    
    Movie createMovie(Movie movie) throws Exception;

    Movie getMovieById(ObjectId id) throws Exception;

    List<Movie> getAllMovies() throws Exception;

    Movie updateMovie(ObjectId id, Movie movie) throws Exception;

    void deleteMovie(ObjectId id) throws Exception;
}
