package com.example.library.services;

import com.example.library.model.Category;
import com.example.library.model.dto.CategoryDto;

public interface CategoryServices {

    CategoryDto createCategory(CategoryDto category);
    CategoryDto getCategory(Long id);

    Boolean deleteCategory(Long id);
}
