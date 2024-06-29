package com.sl.foodorderingsystem.serviceImpl;

import com.sl.foodorderingsystem.Repository.BillRespository;
import com.sl.foodorderingsystem.Repository.CategoryRepository;
import com.sl.foodorderingsystem.Repository.ProductRepository;
import com.sl.foodorderingsystem.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillRespository billRespository;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<String, Object>();
          map.put("category" , categoryRepository.count());
          map.put("product" , productRepository.count());
          map.put("bill" , billRespository.count());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
