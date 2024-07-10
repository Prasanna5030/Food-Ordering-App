package com.sl.foodorderingsystem.Repository;

import com.sl.foodorderingsystem.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {
    //where c.id in (select p.category from Product p where p.status='true')"
    @Query("SELECT c FROM Category c WHERE c.id IN (SELECT p.category.id FROM Product p WHERE p.status = 'true')")
    List<Category> getAllCategory();
}

