package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {
    List<Emprendimiento> getByTag(String tag);
    List<Emprendimiento> getByPublished(Boolean published);
}
