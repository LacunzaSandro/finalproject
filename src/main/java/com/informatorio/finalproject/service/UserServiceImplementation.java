package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.UserLoginDto;
import com.informatorio.finalproject.dto.VoteUserResponse;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll(String date, String city) {
        if (date != null) {
            return userRepository.findByCreateAtBefore(LocalDate.parse(date));
        } else if (city != null) {
            return userRepository.findByCity(city);
        } else {
            return userRepository.findAll();
        }

    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll(Example<User> user) {
        return userRepository.findAll(user);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<VoteUserResponse> findVoteOfUser(Long id) {
        return userRepository.findVoteOfUser(id);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String pwd) {
        return userRepository.findUserByEmailAndPassword(email, pwd);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Iterable<User> findByCreateAtBefore(LocalDate date) {
        return userRepository.findByCreateAtBefore(date);
    }

    @Override
    public Iterable<User> findByCity(String city) {
        return userRepository.findByCity(city);
    }


}
