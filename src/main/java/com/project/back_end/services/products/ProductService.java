package com.project.back_end.services.products;

import org.bson.types.ObjectId;

import com.project.back_end.dtos.ProductsDTO;
import com.project.back_end.models.Product;
import com.project.back_end.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Locale.Category;

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


    // @Override @Transactional
    // public Product ceateProduct(ProductsDTO productDTO) throws Exception {
    //    Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
    //            .orElseThrow(() -> new UserDataExpection(productDTO.getCategoryId()));
    //    Brand existingBrand = brandRepository.findById(productDTO.getBrandId())
    //            .orElseThrow(() -> new UserDataExpection(productDTO.getBrandId()));
    //    Product product = Product.builder()
    //            .name(productDTO.getName())
    //            .brandId(productDTO.getBrandId())
    //            .categoryId(productDTO.getCategoryId())
    //            .price(productDTO.getPrice())
    //            .description(productDTO.getDescription())
    //            .imageUrls(productDTO.getImageUrls())
    //            .build();
    //    return productRepository.save(product);
    // }
}
