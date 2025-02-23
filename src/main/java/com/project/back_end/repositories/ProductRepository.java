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

    // Lấy tất cả sản phẩm
    List<Product> findAll(); 

    // Tìm sản phẩm theo Category ID
    List<Product> findByCategoryId(String categoryId);

    // Tìm sản phẩm theo Brand ID
    List<Product> findByBrandId(String brandId);

    // Lấy chi tiết sản phẩm theo ID
    Optional<Product> findById(String id);

    @Query("{ '_id': ?0 }")
    Optional<Product> getDetailProductById(ObjectId id);
}
