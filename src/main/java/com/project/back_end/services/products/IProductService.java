package com.project.back_end.services.products;
import java.util.List;
import org.bson.types.ObjectId;
import com.project.back_end.dtos.ProductsDTO;
import com.project.back_end.models.Product;

public interface IProductService {
    Product createProduct(ProductsDTO productDTO) throws Exception;
    Product getProductById(ObjectId id) throws Exception;
    Product updateProduct(ObjectId id, ProductsDTO productDTO) throws Exception;
    void deleteProduct(ObjectId id) throws Exception;
    List<Product> getAllProducts() throws Exception;
}
