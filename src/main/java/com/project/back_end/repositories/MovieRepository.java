package com.project.back_end.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.back_end.models.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    
    List<Movie> findAll();

    List<Movie> findByGenres(String genre);

    List<Movie> findByTitle(String title);

    Optional<Movie> findById(ObjectId id);

    @Query("{ 'title': ?0 }")
    Optional<Movie> getDetailMovieByTitle(String title);
}
