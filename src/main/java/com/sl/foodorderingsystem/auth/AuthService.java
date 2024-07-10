package com.sl.foodorderingsystem.auth;

import com.sl.foodorderingsystem.JWT.JwtService;
import com.sl.foodorderingsystem.Repository.UserRepository;
import com.sl.foodorderingsystem.config.SecurityConfig;
import com.sl.foodorderingsystem.entity.Role;
import com.sl.foodorderingsystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final UserRepository userRepository;
    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfig securityConfig;

    public ResponseEntity<AuthenticationResponse> register(Map<String , String> requestMap) {

        if(validateSignUpMap(requestMap)) {
            Optional<User> optUser = userRepository.findByEmail(requestMap.get("email"));

            var registerRequest= getUserFromMap(requestMap);

            if (!optUser.isPresent()) {

                var user = User.builder()
                        .firstName(registerRequest.getFirstName())
                        .lastName(registerRequest.getLastName())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .role(registerRequest.getRole())
                        .status("false")
                        .build();
                var savedUser = userRepository.save(user);
                String jwtToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder().message("Successfully Registered").accessToken(jwtToken).build();
                return ResponseEntity.ok(authResponse);
            } else {
                var authResponse = AuthenticationResponse.builder().message("Invalid user details. user already exists").build();
                return new ResponseEntity<AuthenticationResponse>(authResponse, HttpStatus.BAD_REQUEST);
            }
        }
        else{
            var authResponse = AuthenticationResponse.builder().message("Invalid details").build();
            return new ResponseEntity<AuthenticationResponse>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }



    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        //FirstStep
        //We need to validate our request (validate whether password & username is correct)
        //Verify whether user present in the database
        //Which AuthenticationProvider -> DaoAuthenticationProvider (Inject)
        //We need to authenticate using authenticationManager injecting this authenticationProvider
        //SecondStep
        //Verify whether userName and password is correct => UserNamePasswordAuthenticationToken
        //Verify whether user present in db
        //generateToken
        //Return the token

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

//       if(securityConfig.isAdmin(auth)){
//           System.out.println("The user is admin  "+auth);
//       }
//       else{
//           System.out.println("The user is user"+auth);
//
//       }

            if (auth.isAuthenticated()) {
                var user = userRepository.findByEmail(request.getEmail());


                String jwtToken = jwtService.generateToken(user.get());
                var authResponse = AuthenticationResponse.builder().message("successfully logged in").accessToken(jwtToken).build();
                return new ResponseEntity<>(authResponse, HttpStatus.OK);
            } else {
                var authResponse = AuthenticationResponse.builder().message("Invalid email or password").build();
                return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        var authResponse = AuthenticationResponse.builder().message("Invalid email or Password. please enter valid details").build();
        return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
    }

    private RegisterRequest getUserFromMap(Map<String, String> requestMap) {

        return RegisterRequest.builder()
                .firstName(requestMap.get("firstName"))
                .lastName(requestMap.get("lastName"))
                .email(requestMap.get("email"))
                .password(requestMap.get("password"))
                .role(Role.USER)
                .build();
    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {

        if(requestMap.containsKey("firstName") && requestMap.containsKey("lastName" ) && requestMap.containsKey("email")
          && requestMap.containsKey("password") ) {
            return true;
        }else
            return false;
    }

}


