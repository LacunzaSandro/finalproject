package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.VoteEmprendimientoResponse;
import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.User;
import com.informatorio.finalproject.repository.EmprendimientoRepository;
import com.informatorio.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
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
    public Iterable<Emprendimiento> findAll(String tag, String published) {
        if (tag != null) {
            return emprendimientoRepository.getEmprendimientoByTagLike(tag);
        } else if (published != null) {
            return emprendimientoRepository.getByPublished(Boolean.parseBoolean(published));
        }
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
    public List<Emprendimiento> findAll(Example<Emprendimiento> ex) {
        return emprendimientoRepository.findAll(ex);
    }

    @Override
    @Transactional
    public Emprendimiento save(Emprendimiento emp) {
        return emprendimientoRepository.save(emp);
    }

    @Override
    public void deleteById(Long id) {
        emprendimientoRepository.deleteById(id);
    }

    @Override
    public List<Emprendimiento> getEmprendimientoByTagLike(String tag) {
        return emprendimientoRepository.getEmprendimientoByTagLike(tag);
    }

    //@Override
    //public List<Emprendimiento> getByTag(String tag) {
    //    return emprendimientoRepository.getByTag(tag);
    //}

    @Override
    public List<Emprendimiento> getByPublished(Boolean published) {
        return emprendimientoRepository.getByPublished(published);
    }

    @Override
    public List<VoteEmprendimientoResponse> findVoteOfEmprendimiento(Long id) {
        return emprendimientoRepository.findVoteOfEmprendimiento(id);
    }
}
