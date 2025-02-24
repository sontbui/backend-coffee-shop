package com.project.back_end.repositories;
import com.project.back_end.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAll(); 

    List<Product> findByCategoryId(String categoryId);

    List<Product> findByBrandId(String brandId);

    Optional<Product> findById(ObjectId productId);

    @Query("{ '_id': ?0 }")
    Optional<Product> getDetailProductById(ObjectId id);
}
