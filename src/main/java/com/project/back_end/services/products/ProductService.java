package com.project.back_end.services.products;

import org.bson.types.ObjectId;
import com.project.back_end.models.Product;
import com.project.back_end.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product getProductById(ObjectId productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.getDetailProductById(productId);
        if (optionalProduct.isEmpty()) {
            throw new Exception("Product not found");
        }
        return optionalProduct.get();
    }
}
