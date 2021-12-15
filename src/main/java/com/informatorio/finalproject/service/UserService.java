package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.VoteUserResponse;
import com.informatorio.finalproject.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {
     Iterable<User> findAll(String date, String city);
     Iterable<User> findAll();
     Page<User> findAll(Pageable pageable);
     Optional<User> findById(Long id);
     List<User> findAll(Example<User> user);
     User save(User user);
     void deleteById(Long id);
     Boolean existsByEmail(String email);
     List<VoteUserResponse> findVoteOfUser(Long id);
     Optional<User>  findUserByEmailAndPassword(String email, String pwd);
     User findUserByEmail(String email);
     Iterable<User> findByCreateAtBefore(LocalDate date);
     Iterable<User> findByCity(String city);
}
