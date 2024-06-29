package com.sl.foodorderingsystem.Repository;

import com.sl.foodorderingsystem.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BillRespository extends JpaRepository<Bill,Integer> {


    @Query(value ="select b from Bill b order by b.id desc ")
    List<Bill> getAllBills();

    @Query(value ="select b from Bill b where b.createdBy=:username order by  b.id desc")
    List<Bill> getBillByUserName(@RequestParam("username") String username);
}
