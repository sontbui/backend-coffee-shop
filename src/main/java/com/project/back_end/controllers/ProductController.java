package com.project.back_end.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.project.back_end.models.Product;
import com.project.back_end.responses.ResponseObject;
import com.project.back_end.responses.products.ProductResponse;
import com.project.back_end.services.products.IProductService;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    ObjectMapper objectMapper = new ObjectMapper();
    
    // Constructor injection
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // http://localhost:8088/api/v1/products/6
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(
            @PathVariable("id") ObjectId productId) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            Product existingProduct = productService.getProductById(productId);
            System.out.println("Find product successfull: " + objectMapper.writeValueAsString(existingProduct));
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
}