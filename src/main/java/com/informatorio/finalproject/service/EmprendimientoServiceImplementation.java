package com.informatorio.finalproject.service;

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
import java.util.List;
import java.util.Optional;
@Service
public class EmprendimientoServiceImplementation implements EmprendimientoService {
    @PersistenceContext
    private EntityManager entityManager;
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
    public List<Emprendimiento> findAll(Example<Emprendimiento> ex) {
        return emprendimientoRepository.findAll(ex);
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
    /*
    @Override
    public List<Emprendimiento> getByTag(String tag) {
        return emprendimientoRepository.getByTag(tag);
    }*/

    @Override
    public List<Emprendimiento> getByPublished(Boolean published) {
        return emprendimientoRepository.getByPublished(published);
    }
    /**
    @Override
    public Optional<Emprendimiento> addTagExisted(Long emp_id, Long tag_id) {
        emprendimientoRepository.addTagExisted(emp_id,tag_id);
        return emprendimientoRepository.findById(emp_id);
    }**/
    @Transactional
    @Override
    public Optional<Emprendimiento> addTagExisting(Long emp_id, Long tag_id) {
        entityManager.createNativeQuery("INSERT INTO emprendimiento_tag (fk_emprendimiento, fk_tag) VALUES (?,?)")
                .setParameter(1, emp_id)
                .setParameter(2, tag_id)
                .executeUpdate();
        return emprendimientoRepository.findById(emp_id);
    }

    @Override
    public Boolean findRelationshipWithTag(Long emp_id, Long tag_id) {
        return emprendimientoRepository.findRelationshipWithTag(emp_id,tag_id).isPresent();
    }
}
