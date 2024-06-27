package com.sl.foodorderingsystem.serviceImpl;

import com.sl.foodorderingsystem.JWT.JwtAuthFilter;
import com.sl.foodorderingsystem.Repository.UserRepository;
import com.sl.foodorderingsystem.controller.AdminController;
import com.sl.foodorderingsystem.dto.UserDto;
import com.sl.foodorderingsystem.entity.Role;
import com.sl.foodorderingsystem.entity.User;
import com.sl.foodorderingsystem.service.UserService;
import com.sl.foodorderingsystem.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<String> update(UserDto user) {
        log.info("updating user ----2 ");

//        User userById =userRepository.findById(user.getId()).orElseThrow(()-> new UsernameNotFoundException("user"+user.getId() +"not found"));

     Optional<User> userOptional=   userRepository.findById(user.getId());

        System.out.println("user optional is " + userOptional);

        if(userOptional.isPresent()) {

            userRepository.updateStatus(userOptional.get().getId(), user.getStatus());

            sendMailToUser(user.getStatus(),
                    userOptional.get(),getAllAdmins());

            return new ResponseEntity<String>( "User successfully enabled" , HttpStatus.OK);

        }else{
            return new ResponseEntity<String>( "User doesn't exist" , HttpStatus.BAD_GATEWAY);
        }


        }

    private void sendMailToUser(String status, User user, List<User> admins) {
        if(status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendEmail(user.getEmail(),"User Account approved " ,"Welcome "+user.getFirstName()+" "+user.getLastName()+","+"\n \n \n Your account with username: "+user.getEmail()+"\n is approved by \n ADMIN :"+ jwtAuthFilter.getCurrentUserDetails().getUsername()

                    +"\n Login using your credentials. "
                    +"\n Have a good day."
                    ,admins );
        }
        else{
            emailUtils.sendEmail(user.getEmail(),"User Account approved " ,"Welcome "+user.getFirstName()+" "+user.getLastName()+","+"Your account with username: "+user.getEmail()+"\n is disabled by \n ADMIN :"+ jwtAuthFilter.getCurrentUserDetails().getUsername()

                            +"\n To enable your account please contact our adminstration team "
                            +"\n Have a good day."
                    ,admins );
        }
        }



    @Override
    public List<User> getUsers() {
        return userRepository.findAllByRole(Role.USER);
    }

    @Override
    public List<User> getAllAdmins() {
        return userRepository.findAllByRole(Role.ADMIN);
    }
}


