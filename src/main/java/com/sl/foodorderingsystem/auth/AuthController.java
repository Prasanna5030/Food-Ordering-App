package com.sl.foodorderingsystem.auth;

import com.sl.foodorderingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Map<String ,String> requestMap
    ) {

    return   authService.register(requestMap);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            return authService.authenticate(request);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity<AuthenticationResponse>(
                AuthenticationResponse.builder().message("Internal Server Error").build()
                , HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap){
        try {
            return userService.forgotPassword(requestMap);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/checktoken")
    public ResponseEntity<String> checkToken(){
        try {
            return userService.checkToken();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }






}
