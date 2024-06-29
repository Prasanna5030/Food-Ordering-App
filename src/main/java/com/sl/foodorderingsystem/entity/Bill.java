package com.sl.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name="bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    private String name;

    private String email;

    @Column(name = "contactnumber")
    private String contactNumber;

    @Column(name="paymentmethod")
    private String paymentMethod;

    @Column(name="total")
    private Integer total ;

    @Column(name="productdetails", columnDefinition = "json")
    private String productDetail;

    @Column(name="createdby")
    private String createdBy;
}
