package com.example.library.services.impl;

import com.example.library.model.Category;
import com.example.library.model.PaginatedResponse;
import com.example.library.model.dto.CategoryDto;
import com.example.library.model.mapper.CategoryMapper;
import com.example.library.repository.CategoryRepository;
import com.example.library.services.CategoryServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImpl implements CategoryServices {

    @Autowired
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    private CategoryMapper categoryMapper;
    private PaginationResponseImpl paginationResponse;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryName(categoryDto.getCategoryName());

        Category save = categoryRepository.save(category);
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(save.getCategoryId());

        return categoryDto;
    }


    public PaginatedResponse<CategoryDto> getAllCategories(int size, int pageNo, Sort sort) {


        Pageable page = PageRequest.of(pageNo, size , sort);
        Page<Category> all = categoryRepository.findAll(page);
        List<CategoryDto> collect = all.getContent().stream().map(this::categoryResponse).toList();
        return paginationResponse.buildPaginatedResponse(collect, all);
    }
    @Override
    public CategoryDto getCategory(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            Category category = byId.get();
            CategoryDto dto = new CategoryDto();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryName(category.getCategoryName());
            return dto;
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isPresent()){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;


    }

    // Convert Entity → DTO
//    public CategoryDto categoryResponse(Category category) {
//
//        CategoryDto dto = new CategoryDto();
//        dto.setCategoryId(category.getCategoryId());
//        dto.setCategoryName(category.getCategoryName());
//
//        return dto;
//    }
//
//
//    // Convert DTO → Entity
//    public Category categoryRequest(CategoryDto dto) {
//
//        Category category = new Category();
//        category.setCategoryId(dto.getCategoryId());
//        category.setCategoryName(dto.getCategoryName());
//
//        return category;
//    }
//

//    public CategoryDto categoryResponse(Category category){
//
//        return modelMapper.map(category,CategoryDto.class);
//    }
//
//    public Category categoryRequest(CategoryDto categoryDto){
//
//        return modelMapper.map(categoryDto,Category.class);
//    }
//

    public CategoryDto categoryResponse(Category category){
        return categoryMapper.categoryResponse(category);
    }

    public Category categoryRequest(CategoryDto categoryDto){
        return categoryMapper.categoryRequest(categoryDto);
    }



}


