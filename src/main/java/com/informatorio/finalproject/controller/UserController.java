package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.dto.UserLoginDto;
import com.informatorio.finalproject.dto.UserUpdateDto;
import com.informatorio.finalproject.dto.VoteUserResponse;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.exception.EmailValidationException;
import com.informatorio.finalproject.exception.RecordNotFoundException;
import com.informatorio.finalproject.exception.SimpleException;
import com.informatorio.finalproject.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("users")
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
        if (oUser.isEmpty()){
            throw new RecordNotFoundException("Invalid user id : " + userId);
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
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDto user, @PathVariable(value = "id") Long userId) {
        Optional<User> oUser = userService.findById(userId);
        if (oUser.isEmpty()){
            throw new RecordNotFoundException("Invalid user id : " + userId);
        }
        BeanUtils.copyProperties(user,oUser.get(),"id");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(oUser.get()));
    }
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        if (userService.findById(userId).isEmpty()) {
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
    //find user by body request
    @GetMapping("/matching")
    public List<User> getMatchingUsers(@RequestBody User user) {
        return userService.findAll(Example.of(user));
    }
    //filter by date of user create.
    @PostMapping("search")
    public List<User> searchByDateCreate(@RequestParam(required = false, defaultValue = "") @NotEmpty String create) {
        return userService.findByCreateAtBefore(LocalDate.parse(create));
    }

    //login
    @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST } )
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User userLogin) {
        Optional<User>  oUser = userService.findUserByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());
        if (oUser.isEmpty()){
            throw new SimpleException("Email or password is wrong");
        }
        String token = getJWTToken(oUser.get().getEmail());
        UserLoginDto user = new UserLoginDto();
        user.addUser(oUser.get().getFirstName() + " " + oUser.get().getLastName(), oUser.get().getEmail());
        user.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return token;
    }

}
