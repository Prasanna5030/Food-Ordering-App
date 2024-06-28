package com.sl.foodorderingsystem.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String productName;
    private float price;
    private String description;
    private String status;

    @ManyToOne(targetEntity = Category.class,cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="category_id" , nullable = false )
    private Category category;
}
