package com.project.back_end.services.movies;

import com.project.back_end.models.Movie;
import com.project.back_end.repositories.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MovieService implements IMovie {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        movie.setCreatedAt(Instant.now().toString());
        movie.setUpdatedAt(Instant.now().toString());
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovieById(ObjectId id) throws Exception {
        return movieRepository.findById(id).orElseThrow(() -> new Exception("Movie not found"));
    }

    @Override
    public List<Movie> getAllMovies() throws Exception {
        return movieRepository.findAll();
    }

    @Override
    public Movie updateMovie(ObjectId id, Movie movie) throws Exception {
        Movie existingMovie = getMovieById(id);
        movie.setId(existingMovie.getId());
        movie.setCreatedAt(existingMovie.getCreatedAt());
        movie.setUpdatedAt(Instant.now().toString());
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(ObjectId id) throws Exception {
        movieRepository.deleteById(id.toString());
    }
}
