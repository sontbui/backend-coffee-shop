package com.project.back_end.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.project.back_end.models.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findById(ObjectId id);

    boolean existsByType(String type);

    @Query("{ 'type': ?0, 'name.vi': ?1 }")
    Boolean existsByTypeAndNameVi(String type, String nameVi);

    @Query("{ 'type': ?0, 'name.en': ?1 }")
    Boolean existsByTypeAndNameEn(String type, String nameEn);

    List<Category> findByType(String type);

    @Query("{ 'name.vi': ?1 }")
    List<Category> findByNameVi(String nameVi);

    @Query("{ 'name.en': ?1 }")
    List<Category> findByNameEn(String nameEn);

}
