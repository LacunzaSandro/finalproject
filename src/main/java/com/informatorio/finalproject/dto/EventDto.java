package com.informatorio.finalproject.dto;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.EventEnum;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;
import java.util.List;

public class EventDto {

    private String details;
    private LocalDate finish_at;
    private EventEnum state;
    private List<Emprendimiento> emprendimientos;

    public EventDto() {
    }

    public EventDto(String details, LocalDate finish_at, EventEnum state, List<Emprendimiento> emprendimientos) {
        this.details = details;
        this.finish_at = finish_at;
        this.state = state;
        this.emprendimientos = emprendimientos;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
