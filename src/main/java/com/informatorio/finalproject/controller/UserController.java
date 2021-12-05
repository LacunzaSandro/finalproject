package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.entity.UserEnum;
import com.informatorio.finalproject.utils.exception.EmailValidationException;
import com.informatorio.finalproject.utils.exception.RecordNotFoundException;
import com.informatorio.finalproject.repository.UserRepository;
import com.informatorio.finalproject.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.EnumUtils;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //create a new user
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        if(userService.existsByEmail(user.getEmail())) {
            throw new EmailValidationException("Email duplicate");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }
    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<?> readUserById(@PathVariable(value = "id") Long userId) {
        Optional<User> oUser = userService.findById(userId);
        if (!oUser.isPresent()){
            throw new RecordNotFoundException("Invalid user id : " + userId);
            //return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oUser);
    }
    //update an user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable(value = "id") Long userId) {
        Optional<User> oUser = userService.findById(userId);
        if (!oUser.isPresent()){
            throw new RecordNotFoundException("Invalid user id : " + userId);
        }
        BeanUtils.copyProperties(user,oUser.get(),"id");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(oUser.get()));
    }
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        if (!userService.findById(userId).isPresent()) {
            throw new RecordNotFoundException("Invalid user id : " + userId);
        }
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
    //read all user
    @GetMapping
    public List<User> readAllUser(){
        List<User> userList = StreamSupport
                .stream(userService.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return userList;
    }

}
