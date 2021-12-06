package com.informatorio.finalproject.service;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface UserService {
     Iterable<User> findAll();
     Page<User> findAll(Pageable pageable);
     Optional<User> findById(Long id);
     List<User> findAll(Example<User> user);
     User save(User user);
     void deleteById(Long id);
     Boolean existsByEmail(String email);
}
