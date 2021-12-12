package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.dto.VoteUserResponse;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.exception.EmailValidationException;
import com.informatorio.finalproject.exception.RecordNotFoundException;
import com.informatorio.finalproject.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    //get all vote of an user
    @GetMapping("/{id}/vote")
    public ResponseEntity<?> getAllVoteOfAnUser(@PathVariable(value = "id") Long userId) {
        List<VoteUserResponse> voteUser = userService.findVoteOfUser(userId);
        return ResponseEntity.ok(voteUser);
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
    @GetMapping("/matching")
    public List<User> getMatchingUsers(@RequestBody User user) {
        return userService.findAll(Example.of(user));
    }

}
