package com.sl.foodorderingsystem.service;

import com.sl.foodorderingsystem.dto.ProductDto;
import com.sl.foodorderingsystem.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<Product> addNewProduct(Map<String , String> requestMap);

    ResponseEntity<List<Product>> getAllProducts(String filterValue);

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    ResponseEntity<String> deleteProductByid(Integer id);

    ResponseEntity<String> updateProductStatus(Map<String, String> requestMap);


    ResponseEntity<ProductDto> getProductByid(Integer id);
}
