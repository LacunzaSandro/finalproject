package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.entity.Event;
import com.informatorio.finalproject.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
