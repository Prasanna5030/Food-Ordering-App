package com.sl.foodorderingsystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @GetMapping("/get")
    public String sayHello(){

        return "Secured Endpoint :: admin controller get";
    }
    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping("/post")
    public String sayHi(){

        return "Secured Endpoint :: admin controller post";
    }

}
