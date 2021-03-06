package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.VoteEmprendimientoResponse;
import com.informatorio.finalproject.entity.Emprendimiento;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmprendimientoService {
    Iterable<Emprendimiento> findAll();
    Iterable<Emprendimiento> findAll(String tag, String published);
    Page<Emprendimiento> findAll(Pageable pageable);
    Optional<Emprendimiento> findById(Long id);
    List<Emprendimiento> findAll(Example<Emprendimiento> ex);
    Emprendimiento save(Emprendimiento emp);
    void deleteById(Long id);
    List<Emprendimiento> getEmprendimientoByTagLike(String tag);
    List<Emprendimiento> getByPublished(Boolean published);
    List<VoteEmprendimientoResponse> findVoteOfEmprendimiento(Long id);
}
