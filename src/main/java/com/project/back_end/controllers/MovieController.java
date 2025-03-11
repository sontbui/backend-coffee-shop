package com.project.back_end.controllers;

import com.project.back_end.models.Movie;
import com.project.back_end.responses.ResponseObject;
import com.project.back_end.services.movies.IMovie;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final IMovie movieService;

    @Autowired
    public MovieController(IMovie movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createMovie(@RequestBody Movie movie) {
        try {
            Movie newMovie = movieService.createMovie(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseObject.builder()
                    .data(newMovie)
                    .message("Movie created successfully")
                    .status(HttpStatus.CREATED.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getMovieById(@PathVariable ObjectId id) {
        try {
            Movie movie = movieService.getMovieById(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(movie)
                    .message("Movie retrieved successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.toString())
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllMovies() {
        try {
            List<Movie> movies = movieService.getAllMovies();
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(movies)
                    .message("Movies retrieved successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateMovie(@PathVariable ObjectId id, @RequestBody Movie movie) {
        try {
            Movie updatedMovie = movieService.updateMovie(id, movie);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(updatedMovie)
                    .message("Movie updated successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteMovie(@PathVariable ObjectId id) {
        try {
            movieService.deleteMovie(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Movie deleted successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }
}
