package com.project.back_end.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.project.back_end.dtos.CategoryDTO;
import com.project.back_end.models.Category;
import com.project.back_end.responses.ResponseObject;
import com.project.back_end.services.categories.ICategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category API", description = "APIs for managing categories")
public class CategoryController {
    

    private final ICategory categoryService;
    @Autowired
    public CategoryController(ICategory categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new category", description = "Adds a new category to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Category created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ResponseObject> createCategory(@RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseObject.builder()
                    .message("Request body is null")
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build()
            );
        }
        try {
            Category newCategory = categoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseObject.builder()
                    .data(newCategory)
                    .message("Category created successfully")
                    .status(HttpStatus.CREATED.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

    @Operation(summary = "Update an existing category", description = "Updates category details by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable("id") ObjectId id, @RequestBody CategoryDTO categoryDTO) throws Exception {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(updatedCategory)
                    .message("Category updated successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }


    @Operation(summary = "Delete a category", description = "Removes a category from the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable("id") ObjectId id) throws Exception {
        try {
            Category deleteCategory = categoryService.getCategoryById(id);
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(deleteCategory)
                    .message("Category deleted successfully")
                    .status(HttpStatus.OK.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build());
        }
    }

}
