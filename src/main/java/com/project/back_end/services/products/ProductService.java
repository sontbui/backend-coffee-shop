package com.project.back_end.services.products;

import com.project.back_end.dtos.ProductsDTO;
import com.project.back_end.models.Product;
import com.project.back_end.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new Exception("Product not found");
        }
        return optionalProduct.get();
    }

    @Override
    @Transactional
    public Product createProduct(ProductsDTO productDTO) throws Exception {
//    Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
    //            .orElseThrow(() -> new UserDataExpection(productDTO.getCategoryId()));
    //    Brand existingBrand = brandRepository.findById(productDTO.getBrandId())
    //            .orElseThrow(() -> new UserDataExpection(productDTO.getBrandId()));
        Product product = Product.builder()
                .name(productDTO.getName())
                .brandId(productDTO.getBrandId())
                .categoryId(productDTO.getCategoryId())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .imageUrls(productDTO.getImageUrls())
                .createdAt(productDTO.getCreatedAt())
                .updatedAt(productDTO.getUpdatedAt())
                .build();

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(ObjectId id, ProductsDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        existingProduct.setName(productDTO.getName());
        existingProduct.setBrandId(productDTO.getBrandId());
        existingProduct.setCategoryId(productDTO.getCategoryId());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImageUrls(productDTO.getImageUrls());
        existingProduct.setCreatedAt(productDTO.getCreatedAt());
        existingProduct.setUpdatedAt(productDTO.getUpdatedAt());

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(ObjectId id) throws Exception {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }

    @Override
    @Transactional
    public List<Product> getAllProducts() throws Exception {
        return productRepository.findAll();
    }
}
