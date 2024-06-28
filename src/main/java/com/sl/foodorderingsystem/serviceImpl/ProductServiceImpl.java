package com.sl.foodorderingsystem.serviceImpl;

import com.google.common.base.Strings;
import com.sl.foodorderingsystem.Repository.ProductRepository;
import com.sl.foodorderingsystem.dto.CategoryDto;
import com.sl.foodorderingsystem.dto.ProductDto;
import com.sl.foodorderingsystem.entity.Category;
import com.sl.foodorderingsystem.entity.Product;
import com.sl.foodorderingsystem.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<Product> addNewProduct(Map<String , String> requestMap) {
//        Category category= Category.builder()
//                .id(productDto.getCategoryId())
//                .category(productDto.getCategory())
//                .build();
//
//        Product product = Product.builder()
//                .productName(productDto.getProductName())
//                .price(productDto.getPrice())
//                .status(productDto.getStatus())
//                .description(productDto.getDescription())
//                .category(category)
//                .build();
//
//        Product p1=productRepository.save(product);

        ;
        if(validateProductMap(requestMap ,false)){
            log.info("validation success");
          Product p1=  productRepository.save(getProductFromMap(requestMap, false));
            System.out.println("the product is "+p1);
            return new ResponseEntity<Product>(p1, HttpStatus.OK);
        }
        return new ResponseEntity<Product>((Product) null,HttpStatus.BAD_REQUEST);



    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                return new ResponseEntity<List<Product>>(productRepository.getAllProducts(), HttpStatus.OK);
            } else {
                new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        if(validateProductMap(requestMap, true)){
            Optional<Product> optional = productRepository.findById(Integer.parseInt(requestMap.get("id")));

            if(!optional.isEmpty()){

               Product product = getProductFromMap(requestMap , true);
               product.setStatus(optional.get().getStatus());
               productRepository.save(product);
               return ResponseEntity.ok("Product updated successfully");
            }else{
                return new ResponseEntity<>("Product doesnt exist" , HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>("Something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProductByid(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if(!optional.isEmpty()){
            productRepository.deleteById(id);
            return new ResponseEntity<>("Product deleted successfully" ,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product with" +id+"not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> updateProductStatus(Map<String, String> requestMap) {
        log.info("inside update status -----------1");
        Optional<Product> optional= productRepository.findById(Integer.parseInt(requestMap.get("id")));
        if(!optional.isEmpty()){

            log.info("inside update status----------2");
            productRepository.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
            return ResponseEntity.ok("product status updated successfully" );
        }
        else{
            return new ResponseEntity<>("Product doesnt exist" , HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ProductDto> getProductByid(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if(!optional.isEmpty()){
            var productDto= ProductDto.builder()
                    .productName(optional.get().getProductName())
                    .description(optional.get().getDescription())
                    .price(optional.get().getPrice())
                    .status(optional.get().getStatus())
                    .categoryId(optional.get().getCategory().getId())
                    .category(optional.get().getCategory().getCategory())
                    .build();
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = Category.builder()
                .id(Integer.parseInt(requestMap.get("categoryId"))).build();
        Product product = new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else{
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setProductName( requestMap.get("productName"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Float.parseFloat(requestMap.get("price")));
        return  product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("productName")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId){
                return true;
            }
        }
        return false;
    }
}