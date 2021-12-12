package com.informatorio.finalproject.service;

import com.informatorio.finalproject.entity.Vote;
import com.informatorio.finalproject.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteServiceImplementation implements VoteServicie {
    @Autowired
    private VoteRepository voteRepository;
    @Override
    public Iterable<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Override
    public Page<Vote> findAll(Pageable pageable) {
        return voteRepository.findAll(pageable);
    }

    @Override
    public Optional<Vote> findById(Long id) {
        return voteRepository.findById(id);
    }

    @Override
    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public void deleteById(Long id) {
        voteRepository.deleteById(id);
    }


}
