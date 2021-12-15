package com.informatorio.finalproject.entity;

import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "the event must have any description")
    private String details;
    @CreationTimestamp
    private LocalDate create_at;
    @UpdateTimestamp
    private LocalDate update_at;

    private LocalDate finish_at;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private EventEnum state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = true)
    private List<Emprendimiento> emprendimientos = new ArrayList();
    @NotNull(message = "reward must not be empty")
    private Integer award;

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

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public LocalDate getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDate update_at) {
        this.update_at = update_at;
    }
}
