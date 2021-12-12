package com.informatorio.finalproject.service;

import com.informatorio.finalproject.entity.Tag;
import com.informatorio.finalproject.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VoteServicie {
    Iterable<Vote> findAll();
    Page<Vote> findAll(Pageable pageable);
    Optional<Vote> findById(Long id);
    Vote save(Vote vote);
    void deleteById(Long id);

}
