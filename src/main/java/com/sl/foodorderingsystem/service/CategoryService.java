package com.sl.foodorderingsystem.service;

import com.sl.foodorderingsystem.dto.CategoryDto;
import com.sl.foodorderingsystem.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CategoryService {
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAllCategory(String filterValue);

    ResponseEntity<String> updateCategory(Map<String, String> requestMap);

    ResponseEntity<CategoryDto> getCategory(Integer id);
}
