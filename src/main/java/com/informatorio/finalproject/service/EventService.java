package com.informatorio.finalproject.service;

import com.informatorio.finalproject.entity.Event;
import com.informatorio.finalproject.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EventService {
    Iterable<Event> findAll();
    Page<Event> findAll(Pageable pageable);
    Optional<Event> findById(Long id);
    Event save(Event event);
    void deleteById(Long id);
}
