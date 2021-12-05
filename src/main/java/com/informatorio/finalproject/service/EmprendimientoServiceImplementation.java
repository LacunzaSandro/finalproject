package com.informatorio.finalproject.service;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.repository.EmprendimientoRepository;
import com.informatorio.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class EmprendimientoServiceImplementation implements EmprendimientoService {
    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Emprendimiento> findAll() {
        return emprendimientoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Emprendimiento> findAll(Pageable pageable) {
        return emprendimientoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Emprendimiento> findById(Long id) {
        return emprendimientoRepository.findById(id);
    }

    @Override
    @Transactional
    public Emprendimiento save(Emprendimiento emp) {
        return emprendimientoRepository.save(emp);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        emprendimientoRepository.deleteById(id);
    }

    @Override
    public List<Emprendimiento> getByTag(String tag) {
        return emprendimientoRepository.getByTag(tag);
    }

    @Override
    public List<Emprendimiento> getByPublished(Boolean published) {
        return emprendimientoRepository.getByPublished(published);
    }
}
