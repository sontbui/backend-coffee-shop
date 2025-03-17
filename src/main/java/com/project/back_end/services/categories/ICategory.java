package com.project.back_end.services.categories;

import org.bson.types.ObjectId;

import com.project.back_end.dtos.CategoryDTO;
import com.project.back_end.models.Category;

public interface ICategory {

    Category createCategory(CategoryDTO categoryDTO) throws Exception;

    Category updateCategory(ObjectId id, CategoryDTO categoryDTO) throws Exception;

    void deleteCategory(ObjectId id) throws Exception;

    Category getCategoryById(ObjectId id) throws Exception;

}
