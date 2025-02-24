package com.project.back_end.controllers;

import com.project.back_end.dtos.ProductsDTO;
import com.project.back_end.models.Product;
import com.project.back_end.responses.ResponseObject;
import com.project.back_end.responses.products.ProductResponse;
import com.project.back_end.services.products.IProductService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    // Constructor injection
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(
            @PathVariable("id") ObjectId productId) {
        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(ProductResponse.fromProduct(existingProduct))
                    .message("Get detail product successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.toString())
                    .build());
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductResponse> productResponses = products.stream()
                    .map(ProductResponse::fromProduct)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(productResponses)
                    .message("Get all products successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                    .build());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createProduct(@RequestBody ProductsDTO productDTO) {
        try {
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseObject.builder()
                    .data(ProductResponse.fromProduct(newProduct))
                    .message("Product created successfully")
                    .status(HttpStatus.CREATED.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateProduct(
            @PathVariable("id") ObjectId productId,
            @RequestBody ProductsDTO productDTO) {
        try {
            Product updatedProduct = productService.updateProduct(productId, productDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(ProductResponse.fromProduct(updatedProduct))
                    .message("Product updated successfully")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable("id") ObjectId productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Product deleted successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.toString())
                    .build());
        }
    }
}