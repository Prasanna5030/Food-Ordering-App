package com.sl.foodorderingsystem.controller;

import com.sl.foodorderingsystem.dto.UserDto;
import com.sl.foodorderingsystem.entity.User;
import com.sl.foodorderingsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserService userService;

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

    @GetMapping("/allusers")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create')")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList= userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create')")
    public ResponseEntity<String> updateUser(@RequestBody UserDto user){
        System.out.println(user);
        log.info("updating user  ....1");
       return  userService.update(user);

    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create')")
    public ResponseEntity<List<User>>  getUsers(){
       List<User> userList= userService.getUsers();
       return  ResponseEntity.ok(userList);
    }

    @GetMapping("/admins")
    @PreAuthorize("hasAnyAuthority('admin:read','admin:create')")
    public ResponseEntity<List<User>>  getAdmins(){
        List<User> userList= userService.getAllAdmins();
        return  ResponseEntity.ok(userList);
    }

}
