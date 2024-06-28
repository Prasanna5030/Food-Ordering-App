package com.sl.foodorderingsystem.dto;

import com.sl.foodorderingsystem.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDto {

    private Integer id;
    private String productName;
    private float price;
    private String description;
    private String status;


    private Integer categoryId;
    private String category;

    //(p.id, p.productName,p.description,p.price,p.status ,p.category.id,p.category.category

    public ProductDto(Integer id, String productName , String description , float price ,String status,Integer categoryId, String category){
        this.id=id;
        this.productName=productName;
        this.description=description;
        this.price=price;
        this.status=status;
        this.categoryId=categoryId;
        this.category=category;
    }

}
