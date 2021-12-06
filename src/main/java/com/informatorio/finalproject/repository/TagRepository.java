package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> getByText(String text);
}
