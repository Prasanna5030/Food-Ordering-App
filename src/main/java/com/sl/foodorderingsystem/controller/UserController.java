package com.sl.foodorderingsystem.controller;

import com.sl.foodorderingsystem.Repository.UserRepository;
import com.sl.foodorderingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/home/user")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class UserController {

    @Autowired
    private UserService userService;

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

    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:create','user:read','user:create')")
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword( @RequestBody Map<String, String> requestMap){
        return userService.changePassword(requestMap);

    }





}
