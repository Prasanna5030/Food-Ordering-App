package com.sl.foodorderingsystem.controller;

import com.sl.foodorderingsystem.dto.CategoryDto;
import com.sl.foodorderingsystem.entity.Category;
import com.sl.foodorderingsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/category")
@PreAuthorize("hasAnyRole('ADMIN')")
public class CategoryController {

    @Autowired
     private CategoryService categoryService;
    //testing admin access
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create' )")
    @PostMapping("/hello")
    public String hello(){
        return "hello from category";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create')")
    public ResponseEntity<String> addCategory(@RequestBody Map<String , String> requestMap){
              return categoryService.addNewCategory(requestMap);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create')")
    public ResponseEntity<List<Category>> getAllCategory(@RequestBody(required = false) String filterValue){
        return categoryService.getAllCategory(filterValue);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create')")
    public ResponseEntity<String> updateCategory(@RequestBody Map<String ,String> requestMap){
        return categoryService.updateCategory(requestMap);
    }

    @PostMapping("/getcategory/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:create')")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id){
        return categoryService.getCategory(id);
    }

}
