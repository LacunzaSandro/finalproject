package com.informatorio.finalproject.service;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.Tag;
import com.informatorio.finalproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagService {
    Iterable<Tag> findAll();
    Page<Tag> findAll(Pageable pageable);
    Optional<Tag> findById(Long id);
    Tag save(Tag user);
    void deleteById(Long id);
    Optional<Tag> getByText(String text);

}
