package com.sl.foodorderingsystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/user")
@PreAuthorize("hasAnyRole('USER')")
public class UserController {

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/get")
    public String sayHello(){

        return "Secured Endpoint :: user controller get";
    }

    @PreAuthorize("hasAuthority('user:create')")
    @PostMapping("/post")
    public String sayHi(){

        return "Secured Endpoint :: user controller post";
    }

}
