package com.sl.foodorderingsystem.controller;

import com.sl.foodorderingsystem.dto.ProductDto;
import com.sl.foodorderingsystem.entity.Product;
import com.sl.foodorderingsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/product")
@PreAuthorize("hasAnyRole('ADMIN')")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('admin:read' ,'admin:create')")
    public String helloProduct(){
        return "Hello Product";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('admin:read' ,'admin:create')")
    public  ResponseEntity<Product> addNewProduct(@RequestBody Map<String , String> requestMap) {
        return productService.addNewProduct(requestMap);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin:read' ,'admin:create')")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String filterValue ){

        return productService.getAllProducts(filterValue);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin:read' ,'admin:create')")
    public ResponseEntity<String> getAllProduct(@RequestBody Map<String , String> requestMap){

        return productService.updateProduct(requestMap);
    }

    @PostMapping("/product/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:create')")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id){
        return productService.getProductByid(id);
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:create')")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        return productService.deleteProductByid(id);
    }

    @PostMapping("/updatestatus")
    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:create')")
    public ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap){
        return productService.updateProductStatus(requestMap);
    }



}
