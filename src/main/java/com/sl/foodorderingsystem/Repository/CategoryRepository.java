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
    @Query(value="select c from Category c")
    List<Category> getAllCategory();
}