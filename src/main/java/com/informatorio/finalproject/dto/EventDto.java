package com.informatorio.finalproject.dto;

import com.informatorio.finalproject.entity.Emprendimiento;
import com.informatorio.finalproject.entity.EventEnum;
import org.hibernate.annotations.CreationTimestamp;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventDto {
    @NotBlank(message = "details must not be empty")
    private String details;
    @NotNull(message = "details must not be empty")
    private LocalDateTime finish_at;
    @NotNull(message = "details must not be empty")
    private EventEnum state;
    @NotNull(message = "details must not be empty")
    private Integer award;

    public EventDto() {
    }

    public EventDto(String details, LocalDateTime finish_at, EventEnum state) {
        this.details = details;
        this.finish_at = finish_at;
        this.state = state;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getFinish_at() {
        return finish_at;
    }

    public void setFinish_at(LocalDateTime finish_at) {
        this.finish_at = finish_at;
    }

    public EventEnum getState() {
        return state;
    }

    public void setState(EventEnum state) {
        this.state = state;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }
}
