package com.project.back_end.services.categories;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.back_end.dtos.CategoryDTO;
import com.project.back_end.models.Category;
import com.project.back_end.repositories.CategoryRepository;

@Service
public class CategoryService implements ICategory {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) throws Exception {
        Boolean existsEn = categoryRepository.existsByTypeAndNameEn(categoryDTO.getType(),
                categoryDTO.getName().get("en"));
        Boolean existsVi = categoryRepository.existsByTypeAndNameVi(categoryDTO.getType(),
                categoryDTO.getName().get("vi"));

        if (Boolean.TRUE.equals(existsEn) || Boolean.TRUE.equals(existsVi)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category already exists");
        }
        Category category = Category.builder()
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .createdAt(Instant.now().toString())
                .updatedAt(Instant.now().toString())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(ObjectId id, CategoryDTO categoryDTO) throws Exception {
        if (categoryDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CategoryDTO is null");
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        String nameEn = categoryDTO.getName().get("en");
        String nameVi = categoryDTO.getName().get("vi");
        if (nameEn.isEmpty() || nameVi.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The both name category must not be empty");
        }
        existingCategory.setType(categoryDTO.getType());
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setUpdatedAt(Instant.now().toString());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(ObjectId id) throws Exception {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(existingCategory);
    }

    @Override
    public Category getCategoryById(ObjectId id) throws Exception {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

}
