package com.example.library.model.mapper;

import com.example.library.model.Category;
import com.example.library.model.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "category")
public interface CategoryMapper {
    CategoryDto categoryResponse(Category category);
    Category categoryRequest(CategoryDto categoryDto);



}
