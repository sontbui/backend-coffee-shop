package com.project.back_end.services.products;
import org.bson.types.ObjectId;

import com.project.back_end.dtos.ProductsDTO;
import com.project.back_end.models.Product;

public interface IProductService {

    //  Product createProduct(ProductsDTO productDTO) throws Exception;
    // Product ceateProduct(ProductsDTO product) throws Exception;
    Product getProductById(ObjectId id) throws Exception;
}
