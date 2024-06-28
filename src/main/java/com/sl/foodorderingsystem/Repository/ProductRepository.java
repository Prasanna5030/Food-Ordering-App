package com.sl.foodorderingsystem.Repository;

import com.sl.foodorderingsystem.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Query(value = "select new com.sl.foodorderingsystem.dto.ProductDto(p.id, p.productName, p.description, p.price, p.status , p.category.id, p.category.category) from Product  p")
    List<Product> getAllProducts();

    @Modifying
    @Query(value="update Product p set p.status=:status where p.id=:id")
    void updateProductStatus(@RequestParam("status") String status,@RequestParam("id") int id);
}
