package com.sl.foodorderingsystem.controller;

import com.sl.foodorderingsystem.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/home/dashboard")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class DashBoardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/details")

    public ResponseEntity<Map<String, Object>> getCount() {
        return dashboardService.getCount();

    }
}
