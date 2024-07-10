package com.sl.foodorderingsystem.service;

import com.sl.foodorderingsystem.dto.UserDto;
import com.sl.foodorderingsystem.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;


public interface UserService {

    List<User> getAllUsers();


    ResponseEntity<String> update(UserDto user);

    List<User> getUsers();
    List<User> getAllAdmins();

    ResponseEntity<String> changePassword(Map<String, String> requestMap);

    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);

    ResponseEntity<String> checkToken();
}
