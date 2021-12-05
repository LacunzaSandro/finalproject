package com.informatorio.finalproject.service;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmprendimientoService {
    Iterable<Emprendimiento> findAll();
    Page<Emprendimiento> findAll(Pageable pageable);
    Optional<Emprendimiento> findById(Long id);
    Emprendimiento save(Emprendimiento emp);
    void deleteById(Long id);
    List<Emprendimiento> getByTag(String tag);
    List<Emprendimiento> getByPublished(Boolean published);
}
