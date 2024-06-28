package com.sl.foodorderingsystem.serviceImpl;

import com.google.common.base.Strings;
import com.sl.foodorderingsystem.Repository.CategoryRepository;
import com.sl.foodorderingsystem.dto.CategoryDto;
import com.sl.foodorderingsystem.entity.Category;
import com.sl.foodorderingsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (validateCategoryMap(requestMap, false))
                categoryRepository.save(getCategoryFromMap(requestMap, false));
            return ResponseEntity.ok("category added successfully");
          }catch(Exception e){
            e.printStackTrace();
         }
        return new ResponseEntity<>("internal server error" , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {

        if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
            return new ResponseEntity<List<Category>>(categoryRepository.getAllCategory(),HttpStatus.OK);
        }
         List<Category> categoryList=categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        if(validateCategoryMap(requestMap,true)){
            Optional optional= categoryRepository.findById(Integer.parseInt(requestMap.get("id")));
            if(!optional.isEmpty()){
                categoryRepository.save(getCategoryFromMap(requestMap,true));
                return  ResponseEntity.ok("Category updated successfully");
            }
            else {
                return new ResponseEntity<>("Category doesnt exist",HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Category doesnt exist",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<CategoryDto> getCategory(Integer id) {
        Optional<Category> optional= categoryRepository.findById(id);
        if(!optional.isEmpty()){
            var categoryDto= CategoryDto.builder()
                    .id(optional.get().getId())
                    .Category(optional.get().getCategory())
                    .build();
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {

        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id")&& validateId){
                return true;
            } else if (!validateId) {
                return true;
            }

        }
        return false;
    }

    private Category getCategoryFromMap( Map<String , String> requestMap , boolean isAdd){
        Category category= new Category();
        if( isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setCategory(requestMap.get("name"));
        return  category;
    }
}
