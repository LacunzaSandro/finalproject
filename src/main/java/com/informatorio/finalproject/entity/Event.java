package com.informatorio.finalproject.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.informatorio.finalproject.utils.LocalDateDeserializer;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@ToString
@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String details;
    @CreationTimestamp
    private LocalDate create_at;
    private LocalDate finish_at;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private EventEnum state;
    @OneToMany(targetEntity=Emprendimiento.class, cascade= { CascadeType.MERGE, CascadeType.PERSIST },
            mappedBy="event")
    private List<Emprendimiento> emprendimientos = new ArrayList();


    public Event() {
    }

    public Event(Long id, String details, LocalDate create_at, LocalDate finish_at, EventEnum state, List<Emprendimiento> emprendimientos) {
        this.id = id;
        this.details = details;
        this.create_at = create_at;
        this.finish_at = finish_at;
        this.state = state;
        this.emprendimientos = emprendimientos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    public LocalDate getFinish_at() {
        return finish_at;
    }

    public void setFinish_at(LocalDate finish_at) {
        this.finish_at = finish_at;
    }

    public EventEnum getState() {
        return state;
    }

    public void setState(EventEnum state) {
        this.state = state;
    }

    public List<Emprendimiento> getEmprendimientos() {
        return emprendimientos;
    }

    public void setEmprendimientos(List<Emprendimiento> emprendimientos) {
        this.emprendimientos = emprendimientos;
    }
}
